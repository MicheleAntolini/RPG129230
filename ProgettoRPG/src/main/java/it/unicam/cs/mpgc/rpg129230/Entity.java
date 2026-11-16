package it.unicam.cs.mpgc.rpg129230;

public abstract class Entity {

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

    protected void attackEntity(Entity target) {

        int damage = this.attackPower * 100 / (100 + target.getDefensePower());

        if (damage > 0) {
            target.takeDamage(damage);
        }
    }

    protected void takeDamage(int damage) {
        this.healthPoints -= damage;

        if (healthPoints <= 0) {
            healthPoints = 0;
            this.isAlive = false;
        }
    }

    public boolean isAlive(){
        return this.isAlive;
    }

    protected static int statRandomizer(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
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
