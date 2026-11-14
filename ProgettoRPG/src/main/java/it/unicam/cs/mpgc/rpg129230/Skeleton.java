package it.unicam.cs.mpgc.rpg129230;

public class Skeleton extends Enemies {
    public Skeleton(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public Skeleton() {
        super("Skeleton", 25, 10, 15, 20);
    }

}
