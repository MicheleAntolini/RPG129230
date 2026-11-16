package it.unicam.cs.mpgc.rpg129230.badEntity;

import it.unicam.cs.mpgc.rpg129230.Affinity;

public class Werewolf extends Enemy {
    public Werewolf(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public Werewolf() {
        super("Werewolf", statRandomizer(170, 220), statRandomizer(55, 65), statRandomizer(30, 40), statRandomizer(70, 100));
    }

    @Override
    public Affinity getWeakness() {

        return Affinity.PROTECTION;
    }
}
