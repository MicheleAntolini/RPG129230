package it.unicam.cs.mpgc.rpg129230.item;

import it.unicam.cs.mpgc.rpg129230.enums.Affinity;

public abstract class EquippableItem extends Item{

    private final Affinity affinity;

    protected EquippableItem(String name, int level, Affinity affinity) {
        super(name, level);

        this.affinity = affinity;
    }

    public Affinity getAffinity() {
        return affinity;
    }
}
