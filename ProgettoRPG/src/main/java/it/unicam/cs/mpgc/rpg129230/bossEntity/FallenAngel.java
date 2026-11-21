package it.unicam.cs.mpgc.rpg129230.bossentity;

import it.unicam.cs.mpgc.rpg129230.enums.Affinity;
import it.unicam.cs.mpgc.rpg129230.goodentity.Adventurer;
import it.unicam.cs.mpgc.rpg129230.Dice;

public class FallenAngel extends Boss {

    public FallenAngel(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public FallenAngel() {
        super("Fallen Angel", 300, Dice.roll(60, 75), Dice.roll(55, 70), 10000);
    }

    @Override
    public boolean specialMove(Adventurer target) {
        // Implementation for Fallen Angel's special move
        // It has 70% chance of hitting the target and deals damage equal to 30% of the target's max health.
        if (isInSecondPhase() && Dice.roll(1, 3) == 1){
           if (Dice.roll(1, 10) <= 7){
               target.takeDamage(target.getMaxHealthPoints() * 30 / 100);
           }
           return true;
        }
        return false;
    }

    @Override
    public Affinity getWeakness() {
        return Affinity.PROTECTION;
    }
}
