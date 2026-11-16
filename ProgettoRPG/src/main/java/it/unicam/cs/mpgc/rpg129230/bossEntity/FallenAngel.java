package it.unicam.cs.mpgc.rpg129230.bossEntity;

import it.unicam.cs.mpgc.rpg129230.Affinity;
import it.unicam.cs.mpgc.rpg129230.goodEntity.Adventurer;

public class FallenAngel extends Boss {

    public FallenAngel(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public FallenAngel() {
        super("Fallen Angel", 650, statRandomizer(60, 75), statRandomizer(80, 90), 1000);
    }

    @Override
    public void specialMove(Adventurer target) {
        // Implementation for Fallen Angel's special move
        // It has 70% chance of hitting the target and deals damage equal to 30% of the target's max health.
    }

    @Override
    public Affinity getWeakness() {
        return Affinity.PROTECTION;
    }
}
