package it.unicam.cs.mpgc.rpg129230.badEntity;

import it.unicam.cs.mpgc.rpg129230.Affinity;

public class Vampire extends Enemy {
    public Vampire(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public Vampire() {
        super("Vampire", statRandomizer(140, 180), statRandomizer(55, 65), statRandomizer(30, 40), statRandomizer(70, 90));
    }

    @Override
    public Affinity getWeakness() {

        return Affinity.BLESSED;
    }
}
