package it.unicam.cs.mpgc.rpg129230.dungeon;

import it.unicam.cs.mpgc.rpg129230.Dice;
import it.unicam.cs.mpgc.rpg129230.enums.RoomType;

import java.io.Serializable;

/**
 * Una porta tra cui il giocatore può scegliere.
 * <p>
 * Ogni porta ha tre probabilità, in percentuale, che sommano sempre a 100: trovare
 * un nemico, uno scrigno o una fontana. Il giocatore le vede prima di scegliere,
 * ma resta comunque una scommessa.
 */
public class Door implements Serializable {

    private int treasure;
    private int fountain;
    private int enemy;

    /**
     * Crea una porta con probabilità precise (utile per i test).
     *
     * @param treasure probabilità di trovare uno scrigno
     * @param fountain probabilità di trovare una fontana
     * @param enemy    probabilità di trovare un nemico
     * @throws IllegalArgumentException se le tre percentuali non sommano a 100
     */
    public Door(int treasure, int fountain, int enemy) {

        if (treasure + fountain + enemy != 100) throw new IllegalArgumentException("Percentuali sbagliate");
        this.treasure = treasure;
        this.fountain = fountain;
        this.enemy = enemy;
    }

    /**
     * Crea una porta con probabilità casuali: scrigno 25-35%, nemico 35-55%,
     * e la fontana prende quello che avanza (almeno 10%).
     */
    public Door(){

        treasure = Dice.roll(25, 35);
        enemy = Dice.roll(35, 55);
        fountain = 100 - treasure - enemy;
    }

    /**
     * Apre la porta: tira un dado da 1 a 100 e lo confronta con le probabilità.
     * Per esempio con nemico 50 e scrigno 30: 1-50 nemico, 51-80 scrigno, 81-100 fontana.
     *
     * @return il tipo di stanza trovata
     */
    public RoomType open(){

        int roll = Dice.roll(1, 100);

        if (roll <= enemy){
            return RoomType.ENEMY;
        } else if (roll <= enemy + treasure){
            return RoomType.TREASURE;
        } else {
            return RoomType.FOUNTAIN;
        }
    }

    /**
     * @return la probabilità di trovare un nemico, in percentuale
     */
    public int getEnemy() {
        return enemy;
    }

    /**
     * @return la probabilità di trovare uno scrigno, in percentuale
     */
    public int getTreasure() {
        return treasure;
    }

    /**
     * @return la probabilità di trovare una fontana, in percentuale
     */
    public int getFountain() {
        return fountain;
    }
}