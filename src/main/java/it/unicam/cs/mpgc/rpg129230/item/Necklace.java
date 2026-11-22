package it.unicam.cs.mpgc.rpg129230.item;

import it.unicam.cs.mpgc.rpg129230.enums.Affinity;
import it.unicam.cs.mpgc.rpg129230.goodentity.Adventurer;

/**
 * Collana: aumenta la vita massima dell'eroe. In più, in base all'affinità, ha un
 * effetto che può attivarsi in battaglia quando un attacco base va a segno
 * (vedi {@link it.unicam.cs.mpgc.rpg129230.dungeon.Battle}).
 */
public class Necklace extends EquippableItem {

    /**
     * Crea una collana.
     *
     * @param name     il nome
     * @param level    il livello, da 1 a 3
     * @param affinity l'affinità, che decide l'effetto in battaglia
     */
    public Necklace(String name,int level, Affinity affinity) {
        super(name, level, affinity);
    }

    /**
     * @return il bonus alla vita massima: 10 + 15 per ogni livello (25, 40, 55)
     */
    public int getHealthBoost() {

        return 10 + super.getLevel() * 15;
    }

    @Override
    public boolean equipOn(Adventurer hero) {
        hero.equipNecklace(this);
        return true;
    }

    @Override
    public String getBonusDescription() {
        return " +" + getHealthBoost() + " HP max";
    }
}