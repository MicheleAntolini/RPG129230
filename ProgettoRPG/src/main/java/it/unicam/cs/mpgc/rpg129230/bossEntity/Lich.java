package it.unicam.cs.mpgc.rpg129230.bossEntity;

import it.unicam.cs.mpgc.rpg129230.Affinity;
import it.unicam.cs.mpgc.rpg129230.goodEntity.Adventurer;

public class Lich extends Boss {

    public Lich(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public Lich() {
        super("Lich", 750, statRandomizer(75, 90), statRandomizer(55, 70), 10000);
    }

    @Override
    public void specialMove(Adventurer target) {
        // Implementation for Lich's special move
        // It damage the target once every two turn and deal damage equal to 10% of the target's max health.
    }

    @Override
    public Affinity getWeakness() {
        return Affinity.VITAL;
    }

}


