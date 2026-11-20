package it.unicam.cs.mpgc.rpg129230.badentity;

import it.unicam.cs.mpgc.rpg129230.enums.Affinity;
import it.unicam.cs.mpgc.rpg129230.Dice;

public class Vampire extends Enemy {
    public Vampire(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    @Override
    public Affinity getWeakness() {

        return Affinity.BLESSED;
    }
}
