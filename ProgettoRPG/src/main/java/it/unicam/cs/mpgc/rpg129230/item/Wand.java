package it.unicam.cs.mpgc.rpg129230.item;

import it.unicam.cs.mpgc.rpg129230.enums.Affinity;

/**
 * Bacchetta: l'arma del Mago. Eredita tutto da {@link Weapon}; esiste come classe
 * separata per poter distinguere le armi delle due classi di eroe.
 */
public class Wand extends Weapon {

    /**
     * Crea una bacchetta.
     *
     * @param name     il nome
     * @param level    il livello, da 1 a 3
     * @param affinity l'affinità, oppure {@code null}
     */
    public Wand(String name, int level, Affinity affinity) {
        super(name, level, affinity);
    }

}