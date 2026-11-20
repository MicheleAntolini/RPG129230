package it.unicam.cs.mpgc.rpg129230.badentity;
import it.unicam.cs.mpgc.rpg129230.Dice;

public class Ghost extends Enemy {
    public Ghost(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public Ghost() {
        super("Ghost", Dice.roll(10, 15), Dice.roll(18, 24), 0, Dice.roll(30, 50));
    }

    @Override
    public int getEvasion() {
        return 30;
    }
}
