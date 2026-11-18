package it.unicam.cs.mpgc.rpg129230.badEntity;

import it.unicam.cs.mpgc.rpg129230.Enum.Affinity;
import it.unicam.cs.mpgc.rpg129230.Dice;

public class Werewolf extends Enemy {
    public Werewolf(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public Werewolf() {
        super("Werewolf", Dice.roll(170, 220), Dice.roll(55, 65), Dice.roll(30, 40), Dice.roll(70, 100));
    }

    @Override
    public Affinity getWeakness() {

        return Affinity.PROTECTION;
    }
}
