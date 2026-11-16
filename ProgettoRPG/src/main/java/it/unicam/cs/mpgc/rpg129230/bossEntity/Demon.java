package it.unicam.cs.mpgc.rpg129230.bossEntity;

import it.unicam.cs.mpgc.rpg129230.Affinity;
import it.unicam.cs.mpgc.rpg129230.goodEntity.Adventurer;

public class Demon extends Boss {

    private boolean barrierActive = false;

    public Demon(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public Demon(){
        super("Demon", 800, statRandomizer(65, 85), statRandomizer(60, 75), 1000);
    }

    @Override
    public void specialMove(Adventurer target) {
        // Implementation for Demon's special move
        // It generate a dark coat/shield that cover 50% of the incoming damage, and it last for all the combat
        if (isInSecondPhase()) {
            barrierActive = true;
        }
    }

    @Override
    public void takeDamage(int damage, Affinity attackAffinity) {
        if (barrierActive && attackAffinity != getWeakness()) {
            damage = damage / 2; // Apply 50% damage reduction
        }
        super.takeDamage(damage, attackAffinity);
    }

    @Override
    public Affinity getWeakness() {
        return Affinity.BLESSED;
    }
}
