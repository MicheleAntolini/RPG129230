package it.unicam.cs.mpgc.rpg129230.badentity;
import it.unicam.cs.mpgc.rpg129230.Dice;

/**
 * Zombie, nemico del livello 1: un po' più resistente dello Scheletro.
 */
public class Zombie extends Enemy {

    /**
     * Crea uno Zombie con statistiche casuali, come nel gioco.
     */
    public Zombie() {
        super("Zombie", Dice.roll(30, 38), Dice.roll(15, 25), Dice.roll(18, 25), Dice.roll(70, 85));
    }

}