package it.unicam.cs.mpgc.rpg129230;

import it.unicam.cs.mpgc.rpg129230.Enum.Affinity;
import it.unicam.cs.mpgc.rpg129230.Enum.AttackType;
import it.unicam.cs.mpgc.rpg129230.Enum.BattleAction;
import it.unicam.cs.mpgc.rpg129230.badEntity.Enemy;
import it.unicam.cs.mpgc.rpg129230.badEntity.Vampire;
import it.unicam.cs.mpgc.rpg129230.goodEntity.Adventurer;
import it.unicam.cs.mpgc.rpg129230.goodEntity.Knight;
import it.unicam.cs.mpgc.rpg129230.item.Potion;
import it.unicam.cs.mpgc.rpg129230.item.Sword;
import it.unicam.cs.mpgc.rpg129230.item.Weapon;

public class Main {
    static void main(String[] args) {

        Adventurer hero = new Knight("Test", 150, 15, 25, 1, 0);
        Enemy vampire = new Vampire("Vampire", 160, 60, 35, 80);
        Weapon sword = new Sword("Spada Benedetta", 1, Affinity.BLESSED);
        hero.getInventory().addItem(sword);
        hero.equipWeapon(sword);


        System.out.println("Inizio: " + hero.getName() + " " + hero.getHealthPoints() + " HP " + " contro "
                + vampire.getName() + " " + vampire.getHealthPoints() + " HP ");


        Battle battle = new Battle(hero, vampire);

        while (!battle.isBattleOver()) {

            if (battle.isAdventurerTurn()) {
                battle.playerTurn(BattleAction.ATTACK, AttackType.BASIC,  null);
                System.out.println("L'eroe attacca");
            } else {
                battle.enemyTurn();
                System.out.println("Lo vampire attacca");
            }

            System.out.println(hero.getName() + " " + hero.getHealthPoints() + " HP | " + vampire.getName() + ": " + vampire.getHealthPoints() + " HP");
        }

        battle.endBattle();

        if (hero.isAlive()) {
            System.out.println("Hai vinto! EXP: " + hero.getExperience() + ", livello: " + hero.getLevel());
        } else {
            System.out.println("Hai perso...");
        }
    }
}
