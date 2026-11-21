package it.unicam.cs.mpgc.rpg129230.badentity;
import it.unicam.cs.mpgc.rpg129230.Dice;

/**
 * Fantasma, nemico del livello 2: poca vita e difesa nulla,
 * ma è difficile da colpire perché schiva il 30% degli attacchi.
 */
public class Ghost extends Enemy {

    /**
     * Crea un Fantasma con statistiche casuali, come nel gioco.
     */
    public Ghost() {
        super("Ghost", Dice.roll(10, 15), Dice.roll(18, 24), 0, Dice.roll(200, 220));
    }

    /**
     * @return 30: il Fantasma schiva circa un attacco su tre
     */
    @Override
    public int getEvasion() {
        return 30;
    }
}