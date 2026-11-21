package it.unicam.cs.mpgc.rpg129230.badentity;

import it.unicam.cs.mpgc.rpg129230.enums.Affinity;
import it.unicam.cs.mpgc.rpg129230.Dice;

/**
 * Lupo Mannaro, nemico forte del livello 3. È debole alle armi di protezione.
 */
public class Werewolf extends Enemy {

    /**
     * Crea un Lupo Mannaro con statistiche casuali, come nel gioco.
     */
    public Werewolf() {
        super("Werewolf", Dice.roll(130, 170), Dice.roll(35, 45), Dice.roll(30, 40), Dice.roll(210, 260));
    }

    /**
     * @return {@link Affinity#PROTECTION}: le armi di protezione gli fanno il 30% di danno in più
     */
    @Override
    public Affinity getWeakness() {

        return Affinity.PROTECTION;
    }
}