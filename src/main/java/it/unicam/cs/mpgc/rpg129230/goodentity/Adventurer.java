package it.unicam.cs.mpgc.rpg129230.goodentity;

import it.unicam.cs.mpgc.rpg129230.enums.Affinity;
import it.unicam.cs.mpgc.rpg129230.enums.Stat;
import it.unicam.cs.mpgc.rpg129230.item.Inventory;
import it.unicam.cs.mpgc.rpg129230.badentity.Enemy;
import it.unicam.cs.mpgc.rpg129230.Entity;
import it.unicam.cs.mpgc.rpg129230.item.Necklace;
import it.unicam.cs.mpgc.rpg129230.item.Weapon;
import it.unicam.cs.mpgc.rpg129230.Dice;

/**
 * L'eroe controllato dal giocatore.
 * <p>
 * Rispetto a {@link Entity} aggiunge livello, esperienza e un inventario (l'eroe
 * "ha un" inventario: composizione). Calcola anche le statistiche totali sommando
 * i bonus degli oggetti equipaggiati.
 * <p>
 * È astratta perché si gioca sempre come {@link Knight} o {@link Mage}: ogni classe
 * decide da sola quali armi può usare ({@link #canEquip}).
 */
public abstract class Adventurer extends Entity {

    private int level;
    private int experience = 0;
    private final Inventory inventory = new Inventory();

    /**
     * Crea un eroe con le statistiche date.
     *
     * @param name            il nome
     * @param maxHealthPoints la vita massima
     * @param attackPower     l'attacco base
     * @param defensePower    la difesa base
     * @param level           il livello di partenza
     * @param experience      l'exp di partenza
     */
    protected Adventurer(String name, int maxHealthPoints, int attackPower, int defensePower, int level, int experience) {
        super(name, maxHealthPoints, attackPower, defensePower);

        this.level = level;
        this.experience = experience;
    }

    /**
     * Riceve l'exp di un nemico sconfitto ed eventualmente sale di livello.
     *
     * @param enemy il nemico appena battuto
     */
    public void gainExperience(Enemy enemy) {

        if (!enemy.isAlive()){
            this.experience += enemy.getExperienceReward();
        }

        levelUp();
    }

    /**
     * Dice se questa classe di eroe può usare l'arma data.
     *
     * @param weapon l'arma da controllare
     * @return {@code true} se l'eroe può equipaggiarla
     */
    public abstract boolean canEquip(Weapon weapon);

    /**
     * Equipaggia un'arma, ma solo se la classe dell'eroe può usarla.
     *
     * @param weapon l'arma da equipaggiare
     */
    public void equipWeapon(Weapon weapon) {

        if (canEquip(weapon)) {
            inventory.equipWeapon(weapon);
        }
    }

    /**
     * Equipaggia una collana aggiornando la vita: la vita massima e quella attuale
     * cambiano solo della differenza tra la collana nuova e quella vecchia.
     * Così scambiare collane non permette di curarsi.
     *
     * @param newNecklace la collana da equipaggiare
     */
    public void equipNecklace(Necklace newNecklace) {
        int oldBonus = 0;
        if (inventory.getNecklace() != null) {
            oldBonus = inventory.getNecklace().getHealthBoost();
        }
        int difference = newNecklace.getHealthBoost() - oldBonus;

        inventory.equipNecklace(newNecklace);

        maxHealthPoints += difference;
        healthPoints += difference;

        // la vita resta sempre tra 1 e il massimo
        if (healthPoints > maxHealthPoints) {
            healthPoints = maxHealthPoints;
        }
        if (healthPoints < 1) {
            healthPoints = 1;
        }
    }

    /**
     * Sale di livello finché l'exp basta (anche più livelli insieme); l'exp in eccesso resta.
     * A ogni livello: vita massima +10-20 e vita piena, attacco e difesa +4-7.
     */
    private void levelUp() {
        while (experience >= getXpToLevelUp()) {
            experience -= getXpToLevelUp();
            level++;

            int healthBonus = Dice.roll(10, 20);
            maxHealthPoints += healthBonus;
            healthPoints = maxHealthPoints;

            attackPower += Dice.roll(4, 7);
            defensePower += Dice.roll(4, 7);
        }
    }

    /**
     * Effetto della fontana: aumenta del 10% la statistica scelta (almeno di 1).
     * Per la vita aumentano sia la vita massima sia quella attuale.
     *
     * @param stat la statistica scelta dal giocatore
     */
    public void useFountain(Stat stat) {
        switch (stat) {
            case HEALTH -> {
                int bonus = Math.max(1, maxHealthPoints * 10 / 100);
                maxHealthPoints += bonus;
                healthPoints += bonus;
            }
            case ATTACK -> attackPower += Math.max(1, attackPower * 10 / 100);
            case DEFENSE -> defensePower += Math.max(1, defensePower * 10 / 100);
        }
    }

    /**
     * @return il livello attuale dell'eroe
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return l'attacco base più il bonus dell'arma equipaggiata
     */
    public int getTotalAttack(){
        if(inventory.getWeapon() == null){
            return this.getAttackPower();
        }
        return this.getAttackPower() + inventory.getWeapon().getBonusDamage();
    }

    /**
     * @return la difesa base più il bonus dell'anello equipaggiato
     */
    public int getTotalDefense() {
        if (inventory.getRing() == null) {
            return getDefensePower();
        }
        return getDefensePower() + inventory.getRing().getBonusDefense();
    }

    /**
     * @return l'affinità dell'arma equipaggiata, oppure {@code null} se non c'è o non ne ha
     */
    public Affinity getWeaponAffinity(){
        if (inventory.getWeapon() == null){
            return null;
        }
        return inventory.getWeapon().getAffinity();
    }

    /**
     * @return l'affinità dell'anello equipaggiato, oppure {@code null} se non c'è
     */
    public Affinity getRingAffinity() {
        if (inventory.getRing() == null) {
            return null;
        }
        return inventory.getRing().getAffinity();
    }

    /**
     * @return l'affinità della collana equipaggiata, oppure {@code null} se non c'è
     */
    public Affinity getNecklaceAffinity() {
        if (inventory.getNecklace() == null) {
            return null;
        }
        return inventory.getNecklace().getAffinity();
    }

    /**
     * Exp necessaria per salire al livello successivo: cresce a ogni livello
     * (100, 125, 150, 175...), così salire diventa via via più impegnativo.
     *
     * @return l'exp necessaria per il prossimo livello
     */
    public int getXpToLevelUp() {
        return 100 + 25 * (level - 1);
    }

    /**
     * @return l'exp accumulata verso il prossimo livello
     */
    public int getExperience() {
        return experience;
    }

    /**
     * @return l'inventario dell'eroe
     */
    public Inventory getInventory() {
        return inventory;
    }
}