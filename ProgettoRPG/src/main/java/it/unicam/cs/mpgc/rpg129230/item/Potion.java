package it.unicam.cs.mpgc.rpg129230.item;

public class Potion extends Item{

    public Potion(String name, int level) {
        super(name, level);
    }

    public int getHealthRestore() {

        return 5 + super.getLevel() * 15;
    }
}
