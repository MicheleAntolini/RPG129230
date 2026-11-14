package it.unicam.cs.mpgc.rpg129230.item;

public class Necklace extends Item {

    public Necklace(String name,int level) {
        super(name, level);
    }

    public int getHealthBoost() {

        return 10 + super.getLevel() * 10;
    }
}
