package it.unicam.cs.mpgc.rpg129230.badEntity;

public class Zombie extends Enemies {
    public Zombie(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public Zombie() {
        super("Zombie", 30, 15, 18, 20);
    }

}
