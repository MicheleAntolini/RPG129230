package it.unicam.cs.mpgc.rpg129230.item;

public class Ring extends Item {

    public Ring(String name, int level) {
        super(name, level);
    }

    public int getBonusDefense() {

        return super.getLevel() * 5;
    }
}
