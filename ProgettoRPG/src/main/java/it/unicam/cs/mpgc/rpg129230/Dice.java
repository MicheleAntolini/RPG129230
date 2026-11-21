package it.unicam.cs.mpgc.rpg129230;
/**
 * Generatore di numeri casuali usato in tutto il gioco
 * (statistiche dei personaggi, iniziativa, probabilità di colpire, scrigni, porte...).
 * Tutta la casualità passa da qui, così la formula è scritta in un solo punto.
 */

public class Dice {
    /**
     * Tira un "dado" e restituisce un numero intero casuale tra min e max,
     * estremi compresi.
     *
     * min il valore più basso che può uscire
     * max il valore più alto che può uscire
     * @return un numero casuale tra min e max
     */
    public static int roll(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }
}
