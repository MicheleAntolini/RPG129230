package it.unicam.cs.mpgc.rpg129230.dungeon;

import it.unicam.cs.mpgc.rpg129230.Dice;
import it.unicam.cs.mpgc.rpg129230.bossentity.Boss;
import it.unicam.cs.mpgc.rpg129230.bossentity.Demon;
import it.unicam.cs.mpgc.rpg129230.bossentity.FallenAngel;
import it.unicam.cs.mpgc.rpg129230.bossentity.Lich;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * L'intero percorso di una partita: tre livelli uno dopo l'altro e, alla fine, il boss.
 * <p>
 * Tiene traccia del livello in cui si trova il giocatore e sceglie a caso il boss
 * all'inizio della partita.
 */
public class Dungeon implements Serializable {

    private static final int LEVELS = 3;

    private final List<Level> levels = new ArrayList<>();
    private int currentLevelIndex = 0;
    private final Boss boss;

    /**
     * Crea i tre livelli e sceglie il boss a caso.
     */
    public Dungeon() {
        // crea i 3 livelli
        for (int i = 1; i <= LEVELS; i++) {
            levels.add(new Level(i));
        }
        // sceglie il boss a caso
        boss = createRandomBoss();
    }

    /**
     * @return uno dei tre boss (Lich, Demone o Angelo Caduto), scelto a caso
     */
    private Boss createRandomBoss() {
        int roll = Dice.roll(1, 3);
        if (roll == 1) {
            return new Lich();
        } else if (roll == 2) {
            return new Demon();
        }
        return new FallenAngel();
    }

    /**
     * @return il livello in cui si trova il giocatore
     */
    public Level getCurrentLevel() {
        return levels.get(currentLevelIndex);
    }

    /**
     * Se il livello attuale è finito, passa al successivo (se esiste).
     * Viene chiamato ogni volta che una stanza è stata risolta.
     */
    public void advanceIfLevelCompleted() {
        if (getCurrentLevel().isCompleted() && currentLevelIndex < LEVELS - 1) {
            currentLevelIndex++;
        }
    }

    /**
     * @return {@code true} se anche l'ultimo livello è finito, cioè tocca al boss
     */
    public boolean isBossTime() {
        return currentLevelIndex == LEVELS - 1 && getCurrentLevel().isCompleted();
    }

    /**
     * @return il boss di questa partita
     */
    public Boss getBoss() {
        return boss;
    }
}