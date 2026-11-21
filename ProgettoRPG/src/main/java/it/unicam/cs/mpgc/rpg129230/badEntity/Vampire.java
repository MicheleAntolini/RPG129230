package it.unicam.cs.mpgc.rpg129230.badentity;

import it.unicam.cs.mpgc.rpg129230.enums.Affinity;
import it.unicam.cs.mpgc.rpg129230.Dice;

public class Vampire extends Enemy {
    public Vampire(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public Vampire() {
        super("Vampire", Dice.roll(120, 160), Dice.roll(35, 45), Dice.roll(30, 40), Dice.roll(200, 250));
    }

    @Override
    public Affinity getWeakness() {

        return Affinity.BLESSED;
    }
}
