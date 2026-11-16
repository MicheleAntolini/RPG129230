package it.unicam.cs.mpgc.rpg129230.goodEntity;

import it.unicam.cs.mpgc.rpg129230.item.Wand;
import it.unicam.cs.mpgc.rpg129230.item.Weapon;

public class Mage extends Adventurer {
    public Mage(String name, int maxHealthPoints, int attackPower, int defensePower, int level, int experience) {
        super(name, maxHealthPoints, attackPower, defensePower, level, experience);
    }

    public Mage() {
        super("Mage", statRandomizer(40, 70), statRandomizer(25, 32), statRandomizer(5, 10), 1, 0);
    }

    public boolean canEquip(Weapon weapon) {
        return weapon instanceof Wand;
    }
}
