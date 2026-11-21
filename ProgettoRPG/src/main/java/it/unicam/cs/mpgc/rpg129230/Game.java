package it.unicam.cs.mpgc.rpg129230;

import it.unicam.cs.mpgc.rpg129230.dungeon.Battle;
import it.unicam.cs.mpgc.rpg129230.dungeon.Chest;
import it.unicam.cs.mpgc.rpg129230.dungeon.Dungeon;
import it.unicam.cs.mpgc.rpg129230.dungeon.Level;
import it.unicam.cs.mpgc.rpg129230.enums.RoomType;
import it.unicam.cs.mpgc.rpg129230.enums.Stat;
import it.unicam.cs.mpgc.rpg129230.goodentity.Adventurer;
import it.unicam.cs.mpgc.rpg129230.item.Item;

import java.io.Serializable;

/**
 * Rappresenta una partita: l'eroe, il dungeon e la battaglia in corso.
 * L'interfaccia (console o SceneBuilder) parla solo con questa classe.
 */
public class Game implements Serializable {

    private final Adventurer hero;
    private final Dungeon dungeon;

    private Battle currentBattle;        // null se non si sta combattendo
    private boolean fountainAvailable;   // true se il giocatore è davanti a una fontana
    private Item lastFoundItem;          // l'ultimo oggetto trovato in uno scrigno

    public Game(Adventurer hero) {
        this.hero = hero;
        this.dungeon = new Dungeon();
    }

    /** Il giocatore sceglie una delle 3 porte (0, 1 o 2). */
    public RoomType enterRoom(int doorIndex) {
        if (isBattleInProgress() || fountainAvailable || dungeon.isBossTime()) {
            throw new IllegalStateException("Non puoi aprire una porta adesso");
        }

        Level level = dungeon.getCurrentLevel();
        RoomType room = level.chooseDoor(doorIndex);

        switch (room) {
            case ENEMY -> currentBattle = new Battle(hero, level.createEnemy());
            case TREASURE -> {
                lastFoundItem = Chest.createRandomItem(hero);
                hero.getInventory().addItem(lastFoundItem);
                dungeon.advanceIfLevelCompleted();
            }
            case FOUNTAIN -> fountainAvailable = true;
        }
        return room;
    }

    /** Il giocatore ha scelto quale statistica potenziare alla fontana. */
    public void useFountain(Stat stat) {
        if (!fountainAvailable) {
            throw new IllegalStateException("Non c'è nessuna fontana");
        }
        hero.useFountain(stat);
        fountainAvailable = false;
        dungeon.advanceIfLevelCompleted();
    }

    /** Fa partire la battaglia contro il boss, dopo il terzo livello. */
    public void startBossBattle() {
        if (!dungeon.isBossTime() || isBattleInProgress()) {
            throw new IllegalStateException("Non è il momento del boss");
        }
        currentBattle = new Battle(hero, dungeon.getBoss());
    }

    /** Da chiamare quando la battaglia è finita: assegna l'exp e chiude la battaglia. */
    public void finishBattle() {
        if (!isBattleInProgress() || !currentBattle.isBattleOver()) {
            throw new IllegalStateException("La battaglia non è finita");
        }
        currentBattle.endBattle();
        currentBattle = null;
        dungeon.advanceIfLevelCompleted();
    }

    public boolean isGameOver() {
        return !hero.isAlive();
    }

    public boolean isVictory() {
        return !dungeon.getBoss().isAlive();
    }

    public boolean isBattleInProgress() {
        return currentBattle != null;
    }

    public boolean isFountainAvailable() {
        return fountainAvailable;
    }

    public boolean isBossTime() {
        return dungeon.isBossTime() && !isVictory();
    }

    public Adventurer getHero() {
        return hero;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public Battle getCurrentBattle() {
        return currentBattle;
    }

    public Item getLastFoundItem() {
        return lastFoundItem;
    }
}
