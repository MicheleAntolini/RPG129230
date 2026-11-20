package it.unicam.cs.mpgc.rpg129230.badentity;
import it.unicam.cs.mpgc.rpg129230.Dice;

public class Skeleton extends Enemy {
    public Skeleton(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public Skeleton() {
        super("Skeleton", Dice.roll(25, 32), Dice.roll(10, 15), Dice.roll(15, 20), Dice.roll(20, 40));
    }
}
