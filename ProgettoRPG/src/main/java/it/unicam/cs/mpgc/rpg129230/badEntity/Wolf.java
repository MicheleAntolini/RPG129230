package it.unicam.cs.mpgc.rpg129230.badentity;
import it.unicam.cs.mpgc.rpg129230.Dice;

/**
 * Lupo, nemico del livello 2: equilibrato tra vita, attacco e difesa.
 */
public class Wolf extends Enemy {
    /**
     * Crea un Lupo con statistiche casuali, come nel gioco.
     */
    public Wolf() {
        super("Wolf", Dice.roll(55, 70), Dice.roll(20, 30), Dice.roll(20, 28), Dice.roll(105, 115));
    }

}