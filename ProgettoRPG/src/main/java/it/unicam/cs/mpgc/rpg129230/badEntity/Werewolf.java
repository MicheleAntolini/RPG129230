package it.unicam.cs.mpgc.rpg129230.badentity;

import it.unicam.cs.mpgc.rpg129230.enums.Affinity;
import it.unicam.cs.mpgc.rpg129230.Dice;

public class Werewolf extends Enemy {
    public Werewolf(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public Werewolf() {
        super("Werewolf", Dice.roll(130, 170), Dice.roll(35, 45), Dice.roll(30, 40), Dice.roll(210, 260));
    }

    @Override
    public Affinity getWeakness() {

        return Affinity.PROTECTION;
    }
}
