package it.unicam.cs.mpgc.rpg129230.item;

import it.unicam.cs.mpgc.rpg129230.enums.Affinity;

/**
 * Anello: aumenta la difesa dell'eroe. Se la sua affinità è la debolezza del boss,
 * riduce anche del 25% i danni di quel boss.
 */
public class Ring extends EquippableItem {

    /**
     * Crea un anello.
     *
     * @param name     il nome
     * @param level    il livello, da 1 a 3
     * @param affinity l'affinità
     */
    public Ring(String name, int level, Affinity affinity) {
        super(name, level, affinity);
    }

    /**
     * @return il bonus alla difesa: +15 per ogni livello (15, 30, 45)
     */
    public int getBonusDefense() {

        return super.getLevel() * 15;
    }
}