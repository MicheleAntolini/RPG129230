package it.unicam.cs.mpgc.rpg129230.badEntity;

public class Wolf extends Enemy {
    public Wolf(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public Wolf() {
        super("Wolf", statRandomizer(55, 70), statRandomizer(20, 30), statRandomizer(20, 28), statRandomizer(30, 50));
    }
}
