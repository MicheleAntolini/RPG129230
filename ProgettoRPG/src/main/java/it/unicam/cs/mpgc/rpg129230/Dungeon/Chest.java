package it.unicam.cs.mpgc.rpg129230.dungeon;

import it.unicam.cs.mpgc.rpg129230.Dice;
import it.unicam.cs.mpgc.rpg129230.enums.Affinity;
import it.unicam.cs.mpgc.rpg129230.goodentity.Adventurer;
import it.unicam.cs.mpgc.rpg129230.goodentity.Knight;
import it.unicam.cs.mpgc.rpg129230.item.*;

public class Chest {

    public static Item createRandomItem(Adventurer hero) {

        int level = Dice.roll(1, 3);
        Affinity affinity = randomAffinity();

        switch (Dice.roll(1, 4)) {
            case 1 -> {
                if (hero instanceof Knight) {
                    return new Sword("Sword " + affinity, level, affinity);
                }
                return new Wand("Wand " + affinity, level, affinity);
            }
            case 2 -> {
                return new Ring("Ring " + affinity, level, affinity);
            }
            case 3 -> {
                return new Necklace("Necklace " + affinity, level, affinity);
            }
            case 4 -> {
                return new Potion("Potion", level);
            }
            default -> throw new IllegalStateException("Tipo di oggetto non valido");
        }
    }

    private static Affinity randomAffinity() {
        switch (Dice.roll(1, 3)) {
            case 1 -> { return Affinity.BLESSED; }
            case 2 -> { return Affinity.VITAL; }
            default -> { return Affinity.PROTECTION; }
        }
    }
}