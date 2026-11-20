package it.unicam.cs.mpgc.rpg129230.badentity;
import it.unicam.cs.mpgc.rpg129230.Dice;

public class Wolf extends Enemy {
    public Wolf(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public Wolf() {
        super("Wolf", Dice.roll(55, 70), Dice.roll(20, 30), Dice.roll(20, 28), Dice.roll(30, 50));
    }

}
