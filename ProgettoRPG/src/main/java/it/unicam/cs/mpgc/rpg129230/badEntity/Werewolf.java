package it.unicam.cs.mpgc.rpg129230.badEntity;

public class Werewolf extends Enemies {
    public Werewolf(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public Werewolf() {
        super("Werewolf", 50, 25, 20, 40);
    }
}
