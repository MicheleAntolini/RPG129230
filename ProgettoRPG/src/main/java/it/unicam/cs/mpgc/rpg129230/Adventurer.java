package it.unicam.cs.mpgc.rpg129230;

public class Adventurer extends Entity {

    private int level;

    public Adventurer(String name, int healthPoints, int attackPower, int defensePower, int experience, boolean alive, int level) {
        super(name, healthPoints, attackPower, defensePower, experience, alive);

        this.level = 1;
    }

    public Adventurer() {
        super("Adventurer",
                (int) (60 + (Math.random() * (110 - 60))),
                (int) (5 + (Math.random() * (12 - 5))),
                (int) (5 + (Math.random() * (12 - 5))),
                0,
                true);
    }

    private void gainExperience(Enemies enemy) {

        if (!enemy.isAlive()){
            this.experience += enemy.experience;
        }
    }

    private void levelUp(){

        if(this.getExperience() >= 100){
            this.level++;
            this.experience = 0;
            this.attackPower += (int) (2 + (Math.random() * (5 - 2)));
            this.defensePower += (int) (2 + (Math.random() * (5 - 2)));
            this.healthPoints += (int) (5 + (Math.random() * (10 - 5)));
        }
    }

    public int getLevel() {
        return level;
    }

}
