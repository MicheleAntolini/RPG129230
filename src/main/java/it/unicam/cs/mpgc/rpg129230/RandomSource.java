package it.unicam.cs.mpgc.rpg129230;

import java.io.Serializable;

/**
 * Qualcosa che sa generare numeri casuali.
 * <p>
 * Le classi che ne hanno bisogno dipendono da questa interfaccia e non da una classe
 * concreta (principio di Dependency Inversion): così si può sostituire la fonte di casualità,
 * per esempio con una prevedibile per i test, senza modificare chi la usa.
 */
public interface RandomSource extends Serializable {

    /**
     * @param min il valore più basso che può uscire
     * @param max il valore più alto che può uscire
     * @return un numero casuale tra {@code min} e {@code max}, estremi compresi
     */
    int roll(int min, int max);
}
