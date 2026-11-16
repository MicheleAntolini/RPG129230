package it.unicam.cs.mpgc.rpg129230.badEntity;

public class Vampire extends Enemies {
    public Vampire(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public Vampire() {
        super("Vampire", 35, 20, 10, 30);
    }
}
