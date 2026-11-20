package it.unicam.cs.mpgc.rpg129230.goodEntity;

import it.unicam.cs.mpgc.rpg129230.Enum.Affinity;
import it.unicam.cs.mpgc.rpg129230.Enum.Stat;
import it.unicam.cs.mpgc.rpg129230.item.Inventory;
import it.unicam.cs.mpgc.rpg129230.badEntity.Enemy;
import it.unicam.cs.mpgc.rpg129230.Entity;
import it.unicam.cs.mpgc.rpg129230.item.Weapon;
import it.unicam.cs.mpgc.rpg129230.Dice;

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

    private void levelUp() {
        while (experience >= XP_TO_LEVEL_UP) {
            experience -= XP_TO_LEVEL_UP;
            level++;

            int healthBonus = Dice.roll(5, 10);
            maxHealthPoints += healthBonus;
            healthPoints += healthBonus;

            attackPower += Dice.roll(2, 5);
            defensePower += Dice.roll(2, 5);
        }
    }

    // fontana: aumenta del 10% la statistica scelta dal giocatore (minimo +1)
    public void useFountain(Stat stat) {
        switch (stat) {
            case HEALTH -> {
                int bonus = Math.max(1, maxHealthPoints * 10 / 100);
                maxHealthPoints += bonus;
                healthPoints += bonus;
            }
            case ATTACK -> attackPower += Math.max(1, attackPower * 10 / 100);
            case DEFENSE -> defensePower += Math.max(1, defensePower * 10 / 100);
        }
    }

    public int getLevel() {
        return level;
    }

    public int getTotalAttack(){
        if(inventory.getWeapon() == null){
            return this.getAttackPower();
        }
        return this.getAttackPower() + inventory.getWeapon().getBonusDamage();
    }

    public Affinity getWeaponAffinity(){
        if (inventory.getWeapon() == null){
            return null;
        }
        return inventory.getWeapon().getAffinity();
    }

    public int getExperience() {
        return experience;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
