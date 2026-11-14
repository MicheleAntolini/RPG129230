package it.unicam.cs.mpgc.rpg129230.bossEntity;

import it.unicam.cs.mpgc.rpg129230.Entity;

public abstract class Boss extends Entity {

    public Boss(String name, int maxHealthPoints, int attackPower, int defensePower) {
        super(name, maxHealthPoints, attackPower, defensePower);
    }
}
