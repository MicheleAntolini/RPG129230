package it.unicam.cs.mpgc.rpg129230.badEntity;

import it.unicam.cs.mpgc.rpg129230.Entity;

public abstract class Enemies extends Entity {

    private final int experienceReward;

    public Enemies(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower);

        this.experienceReward = experienceReward;
    }

    public int getExperienceMob() {

        int experienceMob = this.experienceReward;

        return experienceMob;
    }

}
