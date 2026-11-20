package it.unicam.cs.mpgc.rpg129230.Dungeon;

import it.unicam.cs.mpgc.rpg129230.Dice;
import it.unicam.cs.mpgc.rpg129230.bossEntity.Boss;
import it.unicam.cs.mpgc.rpg129230.bossEntity.Demon;
import it.unicam.cs.mpgc.rpg129230.bossEntity.FallenAngel;
import it.unicam.cs.mpgc.rpg129230.bossEntity.Lich;

import java.util.ArrayList;
import java.util.List;

public class Dungeon {

    private static final int LEVELS = 3;

    private final List<Level> levels = new ArrayList<>();
    private int currentLevelIndex = 0;
    private final Boss boss;

    public Dungeon() {
        // crea i 3 livelli
        for (int i = 1; i <= LEVELS; i++) {
            levels.add(new Level(i));
        }
        // sceglie il boss a caso
        boss = createRandomBoss();
    }

    private Boss createRandomBoss() {
        int roll = Dice.roll(1, 3);
        if (roll == 1) {
            return new Lich();
        } else if (roll == 2) {
            return new Demon();
        }
        return new FallenAngel();
    }

    public Level getCurrentLevel() {
        return levels.get(currentLevelIndex);
    }

    // se il livello attuale è finito, passa al successivo (se c'è)
    public void advanceIfLevelCompleted() {
        if (getCurrentLevel().isCompleted() && currentLevelIndex < LEVELS - 1) {
            currentLevelIndex++;
        }
    }

    // è il momento del boss? Sì, se anche l'ultimo livello è finito
    public boolean isBossTime() {
        return currentLevelIndex == LEVELS - 1 && getCurrentLevel().isCompleted();
    }

    public Boss getBoss() {
        return boss;
    }
}

