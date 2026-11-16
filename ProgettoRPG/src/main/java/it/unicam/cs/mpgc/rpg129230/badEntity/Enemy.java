package it.unicam.cs.mpgc.rpg129230.badEntity;

import it.unicam.cs.mpgc.rpg129230.Affinity;
import it.unicam.cs.mpgc.rpg129230.Entity;

public abstract class Enemy extends Entity {

    private final int experienceReward;

    protected Enemy(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower);

        this.experienceReward = experienceReward;
    }

    public int getExperienceReward() {

        return experienceReward;
    }

    public Affinity getWeakness() {
        return null;
    }

    public void takeDamage(int damage, Affinity attackAffinity) {

        if (attackAffinity == getWeakness() && attackAffinity != null) {
            damage = damage * 130 / 100; // Apply 30% extra damage
        }

        super.takeDamage(damage);
    }
}
