package it.unicam.cs.mpgc.rpg129230.goodEntity;

import it.unicam.cs.mpgc.rpg129230.item.Sword;
import it.unicam.cs.mpgc.rpg129230.item.Weapon;

public class Knight extends Adventurer {
    public Knight(String name, int maxHealthPoints, int attackPower, int defensePower, int level, int experience) {
        super(name, maxHealthPoints, attackPower, defensePower, level, experience);
    }

    public boolean canEquip(Weapon weapon) {
        return weapon instanceof Sword;
    }
}
