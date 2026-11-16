package it.unicam.cs.mpgc.rpg129230.badEntity;

public class Skeleton extends Enemy {
    public Skeleton(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public Skeleton() {
        super("Skeleton", statRandomizer(25, 32), statRandomizer(10, 15), statRandomizer(15, 20), statRandomizer(20, 40));
    }

}
