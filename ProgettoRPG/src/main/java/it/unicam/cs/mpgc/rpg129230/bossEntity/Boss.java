package it.unicam.cs.mpgc.rpg129230.bossentity;

import it.unicam.cs.mpgc.rpg129230.badentity.Enemy;
import it.unicam.cs.mpgc.rpg129230.goodentity.Adventurer;

/**
 * Il nemico finale del dungeon.
 * <p>
 * Un boss è un {@link Enemy} con in più una mossa speciale, diversa per ogni boss,
 * che può usare solo nella "seconda fase", cioè quando gli resta metà vita o meno.
 * Ogni sottoclasse decide da sola quando e come usarla (polimorfismo): la battaglia
 * si limita a chiedere al boss se vuole usarla.
 */
public abstract class Boss extends Enemy {

    /**
     * Crea un boss con le statistiche date.
     *
     * @param name             il nome
     * @param maxHealthPoints  la vita massima
     * @param attackPower      l'attacco
     * @param defensePower     la difesa
     * @param experienceReward l'exp che regala
     */
    protected Boss(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    /**
     * Il boss prova a usare la sua mossa speciale all'inizio del suo turno.
     *
     * @param target l'eroe contro cui combatte
     * @return {@code true} se la mossa ha occupato tutto il turno (quindi il boss non attacca),
     *         {@code false} se il boss deve fare anche l'attacco normale
     */
    public abstract boolean specialMove(Adventurer target);

    /**
     * @return {@code true} se al boss resta metà vita o meno: da qui può usare la mossa speciale
     */
    public boolean isInSecondPhase(){

        return getHealthPoints() <= getMaxHealthPoints() / 2;
    }

}