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
 * <p>
 * È il punto di ingresso del model: l'interfaccia (console o JavaFX) parla solo con
 * questa classe, che coordina le altre. Controlla anche che le azioni vengano fatte
 * nel momento giusto (per esempio non si può aprire una porta durante una battaglia).
 */
public class Game implements Serializable {

    private final Adventurer hero;
    private final Dungeon dungeon;

    private Battle currentBattle;        // null se non si sta combattendo
    private boolean fountainAvailable;   // true se il giocatore è davanti a una fontana
    private Item lastFoundItem;          // l'ultimo oggetto trovato in uno scrigno

    /**
     * Inizia una nuova partita con l'eroe scelto e un dungeon nuovo.
     *
     * @param hero l'eroe del giocatore (Cavaliere o Mago)
     */
    public Game(Adventurer hero) {
        this.hero = hero;
        this.dungeon = new Dungeon();
    }

    /**
     * Il giocatore apre una delle 3 porte del livello attuale.
     * <p>
     * In base alla stanza trovata: con un nemico inizia una battaglia, con uno scrigno
     * l'oggetto finisce nello zaino, con una fontana si aspetta che il giocatore scelga
     * la statistica da potenziare.
     *
     * @param doorIndex la porta scelta (0, 1 o 2)
     * @return il tipo di stanza trovata dietro la porta
     * @throws IllegalStateException se in questo momento non si può aprire una porta
     */
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

    /**
     * Il giocatore ha scelto quale statistica potenziare alla fontana.
     *
     * @param stat la statistica da aumentare
     * @throws IllegalStateException se il giocatore non è davanti a una fontana
     */
    public void useFountain(Stat stat) {
        if (!fountainAvailable) {
            throw new IllegalStateException("Non c'è nessuna fontana");
        }
        hero.useFountain(stat);
        fountainAvailable = false;
        dungeon.advanceIfLevelCompleted();
    }

    /**
     * Fa partire la battaglia contro il boss, dopo aver completato il terzo livello.
     *
     * @throws IllegalStateException se non è ancora il momento del boss
     */
    public void startBossBattle() {
        if (!dungeon.isBossTime() || isBattleInProgress()) {
            throw new IllegalStateException("Non è il momento del boss");
        }
        currentBattle = new Battle(hero, dungeon.getBoss());
    }

    /**
     * Chiude la battaglia appena finita: assegna l'exp all'eroe (se ha vinto)
     * e fa avanzare il percorso.
     *
     * @throws IllegalStateException se non c'è una battaglia o non è ancora finita
     */
    public void finishBattle() {
        if (!isBattleInProgress() || !currentBattle.isBattleOver()) {
            throw new IllegalStateException("La battaglia non è finita");
        }
        currentBattle.endBattle();
        currentBattle = null;
        dungeon.advanceIfLevelCompleted();
    }

    /**
     * @return {@code true} se l'eroe è morto
     */
    public boolean isGameOver() {
        return !hero.isAlive();
    }

    /**
     * @return {@code true} se il boss è stato sconfitto
     */
    public boolean isVictory() {
        return !dungeon.getBoss().isAlive();
    }

    /**
     * @return {@code true} se c'è una battaglia in corso
     */
    public boolean isBattleInProgress() {
        return currentBattle != null;
    }

    /**
     * @return {@code true} se il giocatore è davanti a una fontana e deve scegliere la statistica
     */
    public boolean isFountainAvailable() {
        return fountainAvailable;
    }

    /**
     * @return {@code true} se tutti i livelli sono finiti e il boss non è ancora stato battuto
     */
    public boolean isBossTime() {
        return dungeon.isBossTime() && !isVictory();
    }

    /**
     * @return l'eroe del giocatore
     */
    public Adventurer getHero() {
        return hero;
    }

    /**
     * @return il dungeon della partita
     */
    public Dungeon getDungeon() {
        return dungeon;
    }

    /**
     * @return la battaglia in corso, oppure {@code null} se non si sta combattendo
     */
    public Battle getCurrentBattle() {
        return currentBattle;
    }

    /**
     * @return l'ultimo oggetto trovato in uno scrigno (serve all'interfaccia per mostrarlo)
     */
    public Item getLastFoundItem() {
        return lastFoundItem;
    }
}