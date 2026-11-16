package it.unicam.cs.mpgc.rpg129230.badEntity;

public class Ghost extends Enemy {
    public Ghost(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    public Ghost() {
        super("Ghost", statRandomizer(10, 15), statRandomizer(18, 24), 0, statRandomizer(30, 50));
    }

    public void intangible() {
         //TODO: Implement the intangible ability for the Ghost class, which allows it to avoid damage from physical attacks for a certain percentage.
    }
}
