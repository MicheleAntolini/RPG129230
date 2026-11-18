package it.unicam.cs.mpgc.rpg129230.item;

import it.unicam.cs.mpgc.rpg129230.Enum.Affinity;

public class Ring extends EquippableItem {

    public Ring(String name, int level, Affinity affinity) {
        super(name, level, affinity);
    }

    public int getBonusDefense() {

        return super.getLevel() * 5;
    }
}
