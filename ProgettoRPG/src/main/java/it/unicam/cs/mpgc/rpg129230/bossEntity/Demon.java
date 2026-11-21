package it.unicam.cs.mpgc.rpg129230.bossentity;

import it.unicam.cs.mpgc.rpg129230.enums.Affinity;
import it.unicam.cs.mpgc.rpg129230.goodentity.Adventurer;
import it.unicam.cs.mpgc.rpg129230.Dice;

public class Demon extends Boss {

    private boolean barrierActive = false;

    public Demon(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public Demon(){
        super("Demon", 350, Dice.roll(55, 75), Dice.roll(45, 60), 10000);
    }

    @Override
    public boolean specialMove(Adventurer target) {
        // Implementation for Demon's special move
        // It generate a dark coat/shield that cover 50% of the incoming damage, and it last for all the combat
        if (isInSecondPhase() && !barrierActive) {
            barrierActive = true;
            return true;
        }
        return false;
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
