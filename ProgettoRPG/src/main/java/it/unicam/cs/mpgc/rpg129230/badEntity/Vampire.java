package it.unicam.cs.mpgc.rpg129230.badentity;

import it.unicam.cs.mpgc.rpg129230.enums.Affinity;
import it.unicam.cs.mpgc.rpg129230.Dice;

/**
 * Vampiro, nemico forte del livello 3. È debole alle armi benedette.
 */
public class Vampire extends Enemy {

    /**
     * Crea un Vampiro con statistiche casuali, come nel gioco.
     */
    public Vampire() {
        super("Vampire", Dice.roll(120, 160), Dice.roll(35, 45), Dice.roll(30, 40), Dice.roll(200, 250));
    }

    /**
     * @return {@link Affinity#BLESSED}: le armi benedette gli fanno il 30% di danno in più
     */
    @Override
    public Affinity getWeakness() {

        return Affinity.BLESSED;
    }
}