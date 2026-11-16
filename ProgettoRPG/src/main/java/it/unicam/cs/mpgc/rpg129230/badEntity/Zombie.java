package it.unicam.cs.mpgc.rpg129230.badEntity;

public class Zombie extends Enemy {
    public Zombie(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public Zombie() {
        super("Zombie", statRandomizer(30, 38), statRandomizer(15, 25), statRandomizer(18, 25), statRandomizer(10, 20));
    }

}
