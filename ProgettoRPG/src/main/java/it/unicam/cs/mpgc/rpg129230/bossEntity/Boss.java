package it.unicam.cs.mpgc.rpg129230.bossEntity;

import it.unicam.cs.mpgc.rpg129230.badEntity.Enemy;
import it.unicam.cs.mpgc.rpg129230.goodEntity.Adventurer;

public abstract class Boss extends Enemy {

    protected Boss(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public abstract boolean specialMove(Adventurer target);

    public boolean isInSecondPhase(){

        return getHealthPoints() <= getMaxHealthPoints() / 2;
    }

}
