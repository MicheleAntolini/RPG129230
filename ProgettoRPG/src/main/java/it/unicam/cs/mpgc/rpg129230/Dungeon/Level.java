package it.unicam.cs.mpgc.rpg129230.dungeon;

import it.unicam.cs.mpgc.rpg129230.Dice;
import it.unicam.cs.mpgc.rpg129230.enums.RoomType;
import it.unicam.cs.mpgc.rpg129230.badentity.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Un livello del dungeon: 4 stanze, e per ognuna il giocatore sceglie tra 3 porte.
 * <p>
 * Dopo ogni scelta vengono create 3 porte nuove, con probabilità diverse.
 * Ogni livello ha i suoi nemici: Zombie e Scheletro nel primo, Lupo e Fantasma
 * nel secondo, Vampiro e Lupo Mannaro nel terzo.
 */
public class Level implements Serializable {

    private static final int STEPS = 4;       // stanze per livello
    private static final int DOORS = 3;       // porte tra cui scegliere

    private final int number;                 // 1, 2 o 3
    private int currentStep = 0;              // quante stanze ha già fatto il giocatore
    private final List<Door> doors = new ArrayList<>();

    /**
     * Crea un livello e le sue prime 3 porte.
     *
     * @param number il numero del livello (1, 2 o 3)
     */
    public Level(int number) {
        this.number = number;
        generateDoors();                      // appena nasce il livello, ci sono già 3 porte
    }

    /**
     * Butta via le porte vecchie e ne crea 3 nuove, con probabilità casuali.
     */
    private void generateDoors() {
        doors.clear();
        for (int i = 0; i < DOORS; i++) {
            doors.add(new Door());
        }
    }

    /**
     * Il giocatore apre una porta: si scopre la stanza, si passa alla stanza
     * successiva e si preparano 3 porte nuove.
     *
     * @param index la porta scelta (0, 1 o 2)
     * @return il tipo di stanza trovata
     */
    public RoomType chooseDoor(int index) {
        RoomType room = doors.get(index).open();
        currentStep++;
        generateDoors();
        return room;
    }

    /**
     * @return {@code true} se il giocatore ha già fatto tutte e 4 le stanze
     */
    public boolean isCompleted() {
        return currentStep >= STEPS;
    }

    /**
     * @return il numero del livello (1, 2 o 3)
     */
    public int getNumber() {
        return number;
    }

    /**
     * @return quante stanze ha già fatto il giocatore in questo livello
     */
    public int getCurrentStep() {
        return currentStep;
    }

    /**
     * @return le 3 porte attuali, in una lista di sola lettura
     */
    public List<Door> getDoors() {
        return List.copyOf(doors);
    }

    /**
     * Crea un nemico casuale tra i due tipici di questo livello.
     *
     * @return il nemico da affrontare
     * @throws IllegalStateException se il numero del livello non è 1, 2 o 3
     */
    public Enemy createEnemy() {
        switch (number) {
            case 1 -> {
                if (Dice.roll(1, 2) == 1) {
                    return new Zombie();
                }
                return new Skeleton();
            }
            case 2 -> {
                if (Dice.roll(1, 2) == 1) {
                    return new Wolf();
                }
                return new Ghost();
            }
            case 3 -> {
                if (Dice.roll(1, 2) == 1) {
                    return new Vampire();
                }
                return new Werewolf();
            }
            default -> throw new IllegalStateException("Livello non valido: " + number);
        }
    }
}