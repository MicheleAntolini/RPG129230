package it.unicam.cs.mpgc.rpg129230;

import it.unicam.cs.mpgc.rpg129230.Enum.AttackType;
import it.unicam.cs.mpgc.rpg129230.Enum.BattleAction;
import it.unicam.cs.mpgc.rpg129230.badEntity.Enemy;
import it.unicam.cs.mpgc.rpg129230.goodEntity.Adventurer;

public class Battle {

    private final Adventurer adventurer;
    private final Enemy enemy;
    private boolean defense;
    private boolean dodge;
    private boolean adventurerTurn;

    public Battle(Adventurer adventurer, Enemy enemy) {
        this.adventurer = adventurer;
        this.enemy = enemy;
        initiative();
    }

    private void initiative(){

       int adventurerRoll;
       int enemyRoll;

       do {adventurerRoll = Dice.roll(1, 10); enemyRoll = Dice.roll(1, 10);} while (adventurerRoll == enemyRoll);

       if (adventurerRoll > enemyRoll){
           adventurerTurn = true;
       } else {
           adventurerTurn = false;
       }
    }

    public void playerTurn(BattleAction action, AttackType attackType){

        defense = false;
        dodge = false;

        switch (action){
            case ATTACK -> attack(attackType);
            case DEFEND -> defense = true;
            case DODGE -> dodge = true;
            case USE_ITEM -> {
                //TODO: usare pozione
            }
        }
        adventurerTurn = false;
    }

    public void enemyTurn(){
        int damage = calculateDamage(enemy.getAttackPower(), adventurer.getDefensePower());

        if (defense){
            damage = damage * 70 / 100;
        }

        if (dodge && Dice.roll(1, 10) <= 3){
            //colpo mancato
        } else {
            adventurer.takeDamage(damage);
        }

        adventurerTurn = true;
    }

    private void attack(AttackType attackType){

        switch(attackType){
            case BASIC -> {
                int damage = calculateDamage(adventurer.getTotalAttack(), enemy.getDefensePower());
                enemy.takeDamage(damage, adventurer.getWeaponAffinity());
            }
            case POWER -> {
                //TODO t
            }
            case IMMOBILIZING -> {
                //TODO y-
            }
        }
    }

    private int calculateDamage(int attack, int defense) {
        return attack * 100 / (100 + defense);
    }

    public boolean isBattleOver(){
        if (!adventurer.isAlive() || !enemy.isAlive()){
            return true;
        }
        return false;
    }

    public void endBattle(){
        if (adventurer.isAlive()){
            adventurer.gainExperience(enemy);
        }
    }

    public boolean isAdventurerTurn() {
        return adventurerTurn;
    }

}
