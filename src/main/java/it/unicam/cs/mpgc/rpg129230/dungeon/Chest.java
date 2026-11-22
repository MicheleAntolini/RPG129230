package it.unicam.cs.mpgc.rpg129230.dungeon;

import it.unicam.cs.mpgc.rpg129230.Dice;
import it.unicam.cs.mpgc.rpg129230.enums.Affinity;
import it.unicam.cs.mpgc.rpg129230.goodentity.Adventurer;
import it.unicam.cs.mpgc.rpg129230.goodentity.Knight;
import it.unicam.cs.mpgc.rpg129230.item.*;

/**
 * Lo scrigno: crea un oggetto casuale quando l'eroe ne trova uno nel dungeon.
 */
public class Chest {

    /**
     * Crea un oggetto casuale con livello (1-3) e affinità casuali.
     * Può essere un'arma, un anello, una collana o una pozione. L'arma è sempre
     * quella adatta alla classe dell'eroe: spada per il Cavaliere, bacchetta per il Mago.
     *
     * @param hero l'eroe che apre lo scrigno
     * @return l'oggetto trovato
     */
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

    /**
     * @return una delle tre affinità, scelta a caso
     */
    private static Affinity randomAffinity() {
        switch (Dice.roll(1, 3)) {
            case 1 -> { return Affinity.BLESSED; }
            case 2 -> { return Affinity.VITAL; }
            default -> { return Affinity.PROTECTION; }
        }
    }
}