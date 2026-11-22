package it.unicam.cs.mpgc.rpg129230;

import java.io.Serializable;
/**
 * Qualsiasi essere che può combattere: eroi, nemici e boss.
 * <p>
 * Contiene solo le statistiche comuni (nome, vita, attacco, difesa) e la capacità di
 * subire danni e curarsi. La logica del combattimento non sta qui, ma nella classe
 * Battle.
 *
 * È astratta perché nel gioco non esiste un'entità "generica": si crea sempre
 * un eroe o un nemico specifico.
 */
public abstract class Entity implements Serializable {

    private String name;
    protected int maxHealthPoints;
    protected int healthPoints;
    protected int attackPower;
    protected int defensePower;
    private boolean isAlive;
    /**
     * Crea un'entità con le statistiche date. La vita attuale parte uguale alla vita massima.
     *
     * name            il nome dell'entità
     * maxHealthPoints la vita massima
     * attackPower     l'attacco base
     * defensePower    la difesa base
     */
    protected Entity(String name, int maxHealthPoints, int attackPower, int defensePower) {


        this.name = name;
        this.maxHealthPoints = maxHealthPoints;
        this.healthPoints = maxHealthPoints;
        this.attackPower = attackPower;
        this.defensePower = defensePower;
        this.isAlive = true;

    }


    /**
     * Toglie vita all'entità. La vita non scende mai sotto 0: quando arriva a 0
     * l'entità è considerata morta.
     *
     * damage i punti vita da togliere
     */
    public void takeDamage(int damage) {
        this.healthPoints -= damage;

        if (healthPoints <= 0) {
            healthPoints = 0;
            this.isAlive = false;
        }
    }

    /**
     * Cura l'entità, senza mai superare la vita massima.
     *
     * amount i punti vita da recuperare
     */
    public void heal(int amount) {
        this.healthPoints += amount;

        if (healthPoints > maxHealthPoints) {
            healthPoints = maxHealthPoints;
        }
    }
    /**
     * @return true se l'entità è ancora viva
     */
    public boolean isAlive(){
        return this.isAlive;
    }

    /**
     * @return il nome dell'entità
     */
    public String getName() {
        return name;
    }

    /**
     * @return la vita massima
     */
    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    /**
     * @return la vita attuale
     */
    public int getHealthPoints() {
        return healthPoints;
    }

    /**
     * @return l'attacco base, senza bonus degli oggetti
     */
    public int getAttackPower() {
        return attackPower;
    }

    /**
     * @return la difesa base, senza bonus degli oggetti
     */
    public int getDefensePower() {
        return defensePower;
    }
}
