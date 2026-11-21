package it.unicam.cs.mpgc.rpg129230.goodentity;

import it.unicam.cs.mpgc.rpg129230.Dice;
import it.unicam.cs.mpgc.rpg129230.item.Sword;
import it.unicam.cs.mpgc.rpg129230.item.Weapon;

public class Knight extends Adventurer {
    public Knight(String name, int maxHealthPoints, int attackPower, int defensePower, int level, int experience) {
        super(name, maxHealthPoints, attackPower, defensePower, level, experience);
    }

    public Knight() {
        super("Knight", Dice.roll(110, 160), Dice.roll(8, 13), Dice.roll(20, 28), 1, 0);
        Weapon sword = new Sword("Sword", 1, null);
        getInventory().addItem(sword);
        equipWeapon(sword);
    }

    @Override
    public boolean canEquip(Weapon weapon) {
        return weapon instanceof Sword;
    }

}
