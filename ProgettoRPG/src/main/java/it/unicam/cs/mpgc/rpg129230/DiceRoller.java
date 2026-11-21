package it.unicam.cs.mpgc.rpg129230;

/**
 * Implementazione normale di {@link RandomSource}: usa i dadi veri di {@link Dice}.
 */
public class DiceRoller implements RandomSource {

    @Override
    public int roll(int min, int max) {
        return Dice.roll(min, max);
    }
}
