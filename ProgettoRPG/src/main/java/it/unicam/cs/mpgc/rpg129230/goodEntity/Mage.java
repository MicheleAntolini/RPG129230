package it.unicam.cs.mpgc.rpg129230.goodEntity;

import it.unicam.cs.mpgc.rpg129230.item.Wand;
import it.unicam.cs.mpgc.rpg129230.item.Weapon;
import it.unicam.cs.mpgc.rpg129230.Dice;

public class Mage extends Adventurer {
    public Mage(String name, int maxHealthPoints, int attackPower, int defensePower, int level, int experience) {
        super(name, maxHealthPoints, attackPower, defensePower, level, experience);

    }

    public Mage() {
        super("Mage", Dice.roll(40, 70), Dice.roll(25, 32), Dice.roll(5, 10), 1, 0);
        Weapon wand = new Wand("Bacchetta", 1, null);
        getInventory().addItem(wand);
        equipWeapon(wand);
    }

    @Override
    public boolean canEquip(Weapon weapon) {
        return weapon instanceof Wand;
    }
}
