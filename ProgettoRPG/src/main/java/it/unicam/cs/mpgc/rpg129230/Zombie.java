package it.unicam.cs.mpgc.rpg129230;

public class Zombie extends Enemies {
    public Zombie(String name, int healthPoints, int attackPower, int defensePower, int experience, boolean alive) {
        super(name, healthPoints, attackPower, defensePower, experience, alive);
    }

    public Zombie() {
        super("Zombie", 30, 15, 18, 50, true);
    }


}
