package it.unicam.cs.mpgc.rpg129230.item;

import it.unicam.cs.mpgc.rpg129230.enums.Affinity;

public abstract class Weapon extends EquippableItem {

    protected Weapon(String name, int level, Affinity affinity) {
        super(name, level, affinity);
    }

    public int getBonusDamage() {

        return super.getLevel() * 10;
    }
}
