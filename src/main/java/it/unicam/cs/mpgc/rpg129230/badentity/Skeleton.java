package it.unicam.cs.mpgc.rpg129230.badentity;
import it.unicam.cs.mpgc.rpg129230.Dice;

/**
 * Scheletro, nemico debole del livello 1.
 */
public class Skeleton extends Enemy {
    /**
     * Crea uno Scheletro con statistiche casuali, come nel gioco.
     */
    public Skeleton() {
        super("Skeleton", Dice.roll(25, 32), Dice.roll(10, 15), Dice.roll(15, 20), Dice.roll(75, 90));
    }
}