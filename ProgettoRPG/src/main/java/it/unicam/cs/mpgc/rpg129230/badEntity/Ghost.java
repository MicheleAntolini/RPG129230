package it.unicam.cs.mpgc.rpg129230.badEntity;

public class Ghost extends Enemies {
    public Ghost(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public Ghost() {
        super("Ghost", 25, 10, 20, 15);
    }
}
