package it.unicam.cs.mpgc.rpg129230;

import it.unicam.cs.mpgc.rpg129230.badEntity.Enemy;
import it.unicam.cs.mpgc.rpg129230.badEntity.Zombie;
import it.unicam.cs.mpgc.rpg129230.goodEntity.Adventurer;
import it.unicam.cs.mpgc.rpg129230.goodEntity.Knight;
import it.unicam.cs.mpgc.rpg129230.item.Sword;
import it.unicam.cs.mpgc.rpg129230.item.Weapon;

public class Main {
    static void main(String[] args) {

        Adventurer hero = new Knight();
        Enemy zombie = new Zombie();


        System.out.println("Inizio: " + hero.getName() + " " + hero.getHealthPoints() + " HP " + " contro "
                + zombie.getName() + " " + zombie.getHealthPoints() + " HP ");


        Battle battle = new Battle(hero, zombie);

        while (!battle.isBattleOver()) {

            if (battle.isAdventurerTurn()) {
                battle.playerTurn(BattleAction.ATTACK, AttackType.BASIC);
                System.out.println("L'eroe attacca");
            } else {
                battle.enemyTurn();
                System.out.println("Lo zombie attacca");
            }

            System.out.println(hero.getName() + " " + hero.getHealthPoints() + " HP | " + zombie.getName() + ": " + zombie.getHealthPoints() + " HP");
        }

        battle.endBattle();

        if (hero.isAlive()) {
            System.out.println("Hai vinto! EXP: " + hero.getExperience() + ", livello: " + hero.getLevel());
        } else {
            System.out.println("Hai perso...");
        }
    }
}
