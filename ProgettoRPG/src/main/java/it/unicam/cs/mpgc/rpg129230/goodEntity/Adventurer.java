package it.unicam.cs.mpgc.rpg129230.goodEntity;

import it.unicam.cs.mpgc.rpg129230.badEntity.Enemies;
import it.unicam.cs.mpgc.rpg129230.Entity;
import it.unicam.cs.mpgc.rpg129230.item.Weapon;

public abstract class Adventurer extends Entity {

    private int level = 1;
    private int experience = 0;
    private static final int XP_TO_LEVEL_UP = 100;

    protected Adventurer(String name, int maxHealthPoints, int attackPower, int defensePower, int level, int experience) {
        super(name, maxHealthPoints, attackPower, defensePower);

        this.level = level;
        this.experience = experience;
    }

    public void gainExperience(Enemies enemy) {

        if (!enemy.isAlive()){
            this.experience += enemy.getExperienceMob();
        }

        levelUp();
    }

    public abstract boolean canEquip(Weapon weapon);


    private void levelUp(){

        while (this.getExperience() >= XP_TO_LEVEL_UP) {
            this.level++;
            this.experience = this.experience - XP_TO_LEVEL_UP;
            this.attackPower += (int) (2 + (Math.random() * (5 - 2)));
            this.defensePower += (int) (2 + (Math.random() * (5 - 2)));
            this.healthPoints += (int) (5 + (Math.random() * (10 - 5)));
        }
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }
}
