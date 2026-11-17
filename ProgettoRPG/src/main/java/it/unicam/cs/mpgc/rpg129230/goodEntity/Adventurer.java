package it.unicam.cs.mpgc.rpg129230.goodEntity;

import it.unicam.cs.mpgc.rpg129230.Inventory;
import it.unicam.cs.mpgc.rpg129230.badEntity.Enemy;
import it.unicam.cs.mpgc.rpg129230.Entity;
import it.unicam.cs.mpgc.rpg129230.item.Weapon;

public abstract class Adventurer extends Entity {

    private int level;
    private int experience = 0;
    private static final int XP_TO_LEVEL_UP = 100;
    private final Inventory inventory = new Inventory();

    protected Adventurer(String name, int maxHealthPoints, int attackPower, int defensePower, int level, int experience) {
        super(name, maxHealthPoints, attackPower, defensePower);

        this.level = level;
        this.experience = experience;
    }

    public void gainExperience(Enemy enemy) {

        if (!enemy.isAlive()){
            this.experience += enemy.getExperienceReward();
        }

        levelUp();
    }

    public abstract boolean canEquip(Weapon weapon);

    public void equipWeapon(Weapon weapon) {

        if (canEquip(weapon)) {
            inventory.equipWeapon(weapon);
        }
    }

    private void levelUp(){

        while (this.getExperience() >= XP_TO_LEVEL_UP) {
            this.level++;
            this.experience = this.experience - XP_TO_LEVEL_UP;
            this.attackPower += statRandomizer(4, 9);
            this.defensePower += statRandomizer(4, 9);
            this.maxHealthPoints += statRandomizer(10, 20);
        }
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
