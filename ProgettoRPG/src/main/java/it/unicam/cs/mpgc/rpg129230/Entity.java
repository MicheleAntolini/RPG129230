package it.unicam.cs.mpgc.rpg129230;

public abstract class Entity {

    private String name;
    protected int healthPoints;
    protected int attackPower;
    protected int defensePower;
    protected int experience;
    private boolean alive = true;

    protected Entity(String name, int healthPoints, int attackPower, int defensePower, int experience, boolean alive) {


        this.name = name;
        this.healthPoints = healthPoints;
        this.attackPower = attackPower;
        this.defensePower = defensePower;
        this.experience = experience;
        this.alive = alive;
    }

    protected void attackEntity(Entity target) {
        int damage = this.attackPower - target.getDefensePower();

        if (damage > 0) {
            target.takeDamage(damage);
        }
    }

    protected void takeDamage(int damage) {
        this.healthPoints -= damage;

        if (healthPoints <= 0) {
            healthPoints = 0;
            this.alive = false;
        }
    }

    protected boolean isAlive(){
        return this.alive;
    }

    public String getName() {
        return name;
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

    public int getExperience() {
        return experience;
    }
}
