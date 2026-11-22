package it.unicam.cs.mpgc.rpg129230.item;

import it.unicam.cs.mpgc.rpg129230.goodentity.Adventurer;

/**
 * Pozione di cura: si beve in battaglia e si consuma. Non ha affinità.
 */
public class Potion extends Item{

    /**
     * Crea una pozione.
     *
     * @param name  il nome
     * @param level il livello, da 1 a 3
     */
    public Potion(String name, int level) {
        super(name, level);
    }

    /**
     * @return i punti vita che recupera: 5 + 15 per ogni livello (20, 35, 50)
     */
    public int getHealthRestore() {

        return 5 + super.getLevel() * 15;
    }

    /**
     * Mette la pozione nello slot delle pozioni, se c'è posto.
     */
    @Override
    public boolean equipOn(Adventurer hero) {
        return hero.getInventory().equipPotion(this);
    }

    @Override
    public String getBonusDescription() {
        return " heals " + getHealthRestore() + " HP";
    }
}