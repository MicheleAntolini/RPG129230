package it.unicam.cs.mpgc.rpg129230;

import java.io.Serializable;

public abstract class Entity implements Serializable {

    private String name;
    protected int maxHealthPoints;
    protected int healthPoints;
    protected int attackPower;
    protected int defensePower;
    private boolean isAlive;

    protected Entity(String name, int maxHealthPoints, int attackPower, int defensePower) {


        this.name = name;
        this.maxHealthPoints = maxHealthPoints;
        this.healthPoints = maxHealthPoints;
        this.attackPower = attackPower;
        this.defensePower = defensePower;
        this.isAlive = true;
    }


    public void takeDamage(int damage) {
        this.healthPoints -= damage;

        if (healthPoints <= 0) {
            healthPoints = 0;
            this.isAlive = false;
        }
    }

    public void heal(int amount) {
        this.healthPoints += amount;

        if (healthPoints > maxHealthPoints) {
            healthPoints = maxHealthPoints;
        }
    }

    public boolean isAlive(){
        return this.isAlive;
    }


    public String getName() {
        return name;
    }

    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getDefensePower() {
        return defensePower;
    }
}
