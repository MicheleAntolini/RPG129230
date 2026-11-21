package it.unicam.cs.mpgc.rpg129230.item;

import it.unicam.cs.mpgc.rpg129230.enums.Affinity;

public class Necklace extends EquippableItem {

    public Necklace(String name,int level, Affinity affinity) {
        super(name, level, affinity);
    }

    public int getHealthBoost() {

        return 10 + super.getLevel() * 15;
    }
}
