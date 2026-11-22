package it.unicam.cs.mpgc.rpg129230.badentity;

import it.unicam.cs.mpgc.rpg129230.enums.Affinity;
import it.unicam.cs.mpgc.rpg129230.Entity;

/**
 * Un nemico qualsiasi che l'eroe può incontrare nel dungeon (boss compresi).
 * <p>
 * Rispetto a {@link Entity} aggiunge l'exp che regala quando viene sconfitto, una
 * eventuale debolezza a un'affinità e una eventuale probabilità di schivare.
 * Le sottoclassi sovrascrivono solo quello che le rende diverse.
 */
public abstract class Enemy extends Entity {

    private final int experienceReward;

    /**
     * Crea un nemico con le statistiche date.
     *
     * @param name             il nome del nemico
     * @param maxHealthPoints  la vita massima
     * @param attackPower      l'attacco
     * @param defensePower     la difesa
     * @param experienceReward l'exp che l'eroe riceve sconfiggendolo
     */
    protected Enemy(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower);

        this.experienceReward = experienceReward;
    }

    /**
     * Probabilità (in percentuale) che il nemico schivi un attacco dell'eroe.
     * Di default è 0: solo alcuni nemici la sovrascrivono.
     *
     * @return la probabilità di schivare, da 0 a 100
     */
    public int getEvasion() {
        return 0;
    }

    /**
     * @return l'exp che l'eroe riceve sconfiggendo questo nemico
     */
    public int getExperienceReward() {

        return experienceReward;
    }

    /**
     * L'affinità a cui il nemico è debole. Di default nessuna:
     * solo alcuni nemici e i boss la sovrascrivono.
     *
     * @return l'affinità a cui è debole, oppure {@code null} se non ha debolezze
     */
    public Affinity getWeakness() {
        return null;
    }

    /**
     * Subisce un colpo tenendo conto dell'affinità dell'arma che l'ha colpito:
     * se è proprio la sua debolezza, il danno aumenta del 30%.
     *
     * @param damage         il danno del colpo
     * @param attackAffinity l'affinità dell'arma dell'eroe, oppure {@code null} se non ne ha
     */
    public void takeDamage(int damage, Affinity attackAffinity) {

        if (attackAffinity == getWeakness() && attackAffinity != null) {
            damage = damage * 130 / 100; // +30% se l'arma è la sua debolezza
        }

        super.takeDamage(damage);
    }
}