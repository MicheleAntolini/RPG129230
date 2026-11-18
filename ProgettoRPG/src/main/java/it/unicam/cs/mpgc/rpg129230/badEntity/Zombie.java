package it.unicam.cs.mpgc.rpg129230.badEntity;
import it.unicam.cs.mpgc.rpg129230.Dice;

public class Zombie extends Enemy {
    public Zombie(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public Zombie() {
        super("Zombie", Dice.roll(30, 38), Dice.roll(15, 25), Dice.roll(18, 25), Dice.roll(10, 20));
    }

}
