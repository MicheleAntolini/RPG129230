package it.unicam.cs.mpgc.rpg129230.item;

public abstract class Weapon extends Item {

    protected Weapon(String name, int level) {
        super(name, level);
    }

    public int getBonusDamage() {

        return super.getLevel() * 10;
    }
}
