package it.unicam.cs.mpgc.rpg129230;

public class Skeleton extends Enemies {
    public Skeleton(String name, int healthPoints, int attackPower, int defensePower, int experience, boolean alive) {
        super(name, healthPoints, attackPower, defensePower, experience, alive);
    }

    public Skeleton() {
        super("Skeleton", 25, 10, 15, 50, true);
    }

}
