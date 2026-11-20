package it.unicam.cs.mpgc.rpg129230;

import it.unicam.cs.mpgc.rpg129230.Dungeon.Battle;
import it.unicam.cs.mpgc.rpg129230.Enum.Affinity;
import it.unicam.cs.mpgc.rpg129230.Enum.AttackType;
import it.unicam.cs.mpgc.rpg129230.Enum.BattleAction;
import it.unicam.cs.mpgc.rpg129230.badEntity.Enemy;
import it.unicam.cs.mpgc.rpg129230.bossEntity.Demon;
import it.unicam.cs.mpgc.rpg129230.goodEntity.Adventurer;
import it.unicam.cs.mpgc.rpg129230.goodEntity.Knight;
import it.unicam.cs.mpgc.rpg129230.item.Potion;
import it.unicam.cs.mpgc.rpg129230.item.Sword;
import it.unicam.cs.mpgc.rpg129230.item.Weapon;

public class Main {
    static void main(String[] args) {

        Adventurer hero = new Knight("Test", 500, 40, 30, 1, 0);
        Enemy enemy = new Demon("Demon", 300, 30, 20, 0);
        Weapon sword = new Sword("Spada Benedetta", 1, Affinity.BLESSED);
        hero.getInventory().addItem(sword);
        hero.equipWeapon(sword);
        Potion potion = new Potion("Pozione", 1);
        hero.getInventory().addItem(potion);
        hero.getInventory().equipPotion(potion);


        System.out.println("Inizio: " + hero.getName() + " " + hero.getHealthPoints() + " HP " + " contro "
                + enemy.getName() + " " + enemy.getHealthPoints() + " HP ");


        Battle battle = new Battle(hero, enemy);

        while (!battle.isBattleOver()) {

            if (battle.isAdventurerTurn()) {
                // turno dell'eroe: se sta male e ha una pozione, beve; altrimenti attacca
                if (hero.getHealthPoints() < 50 && !hero.getInventory().getPotionSlot().isEmpty()) {
                    battle.playerTurn(BattleAction.USE_ITEM, null, potion);
                    System.out.println("L'eroe beve una pozione");
                } else {
                    battle.playerTurn(BattleAction.ATTACK, AttackType.BASIC, null);
                    System.out.println("L'eroe attacca");
                }
            } else {
                // turno del vampiro
                battle.enemyTurn();
                System.out.println("Il boss agisce");
            }

            System.out.println(hero.getName() + " " + hero.getHealthPoints() + " HP | " + enemy.getName() + ": " + enemy.getHealthPoints() + " HP");
        }

        battle.endBattle();

        if (hero.isAlive()) {
            System.out.println("Hai vinto! EXP: " + hero.getExperience() + ", livello: " + hero.getLevel());
        } else {
            System.out.println("Hai perso...");
        }
    }
}
