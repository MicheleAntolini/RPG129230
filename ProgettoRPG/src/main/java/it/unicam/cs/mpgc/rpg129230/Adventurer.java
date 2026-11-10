package it.unicam.cs.mpgc.rpg129230;

public class Adventurer extends Entity {

    private int level = 1;
    private static final int XP_TO_LEVEL_UP = 100;

    public Adventurer(String name, int healthPoints, int attackPower, int defensePower, int experience, boolean alive, int level) {
        super(name, healthPoints, attackPower, defensePower, experience, alive);

    }

    public Adventurer() {
        super("Adventurer",
                (int) (60 + (Math.random() * (110 - 60))),
                (int) (5 + (Math.random() * (12 - 5))),
                (int) (5 + (Math.random() * (12 - 5))),
                0,
                true);
    }

    public void gainExperience(Enemies enemy) {

        if (!enemy.isAlive()){
            this.experience += enemy.experience;
        }

        levelUp();
    }



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

}
