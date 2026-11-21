package it.unicam.cs.mpgc.rpg129230.bossentity;

import it.unicam.cs.mpgc.rpg129230.enums.Affinity;
import it.unicam.cs.mpgc.rpg129230.goodentity.Adventurer;
import it.unicam.cs.mpgc.rpg129230.Dice;

public class Lich extends Boss {

    private int countTurn = 0;

    public Lich(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public Lich() {
        super("Lich", 350, Dice.roll(35, 45), Dice.roll(45, 60), 10000);
    }

    @Override
    public boolean specialMove(Adventurer target) {
        // Implementation for Lich's special move
        // It damage the target once every two turn and deal damage equal to 10% of the target's max health.
        countTurn++;
        if (isInSecondPhase() && countTurn % 2 == 0){
            target.takeDamage(target.getMaxHealthPoints() * 10 / 100);
        }
        return false;
    }

    @Override
    public Affinity getWeakness() {
        return Affinity.VITAL;
    }

}


