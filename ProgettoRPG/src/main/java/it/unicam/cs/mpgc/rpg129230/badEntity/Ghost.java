package it.unicam.cs.mpgc.rpg129230.badentity;
import it.unicam.cs.mpgc.rpg129230.Dice;

public class Ghost extends Enemy {
    public Ghost(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    @Override
    public int getEvasion() {
        return 30;
    }
}
