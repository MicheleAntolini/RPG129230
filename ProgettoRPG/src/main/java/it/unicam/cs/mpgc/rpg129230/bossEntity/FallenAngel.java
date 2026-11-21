package it.unicam.cs.mpgc.rpg129230.bossentity;

import it.unicam.cs.mpgc.rpg129230.enums.Affinity;
import it.unicam.cs.mpgc.rpg129230.goodentity.Adventurer;
import it.unicam.cs.mpgc.rpg129230.Dice;

/**
 * Angelo Caduto, uno dei tre boss. È debole alle armi di protezione.
 * <p>
 * Mossa speciale: in seconda fase, circa un turno su tre, lancia un attacco mentale
 * che colpisce al 70% e toglie il 30% della vita massima dell'eroe.
 */
public class FallenAngel extends Boss {

    /**
     * Crea un Angelo Caduto con statistiche precise (utile per i test).
     *
     * @param name             il nome
     * @param maxHealthPoints  la vita massima
     * @param attackPower      l'attacco
     * @param defensePower     la difesa
     * @param experienceReward l'exp che regala
     */
    public FallenAngel(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    /**
     * Crea un Angelo Caduto con le statistiche del gioco. Non dà exp, perché dopo
     * il boss la partita finisce.
     */
    public FallenAngel() {
        super("Fallen Angel", 300, Dice.roll(60, 75), Dice.roll(55, 70), 0);
    }

    /**
     * In seconda fase, con una probabilità su tre, prova l'attacco mentale.
     * Che colpisca o manchi, quel turno è usato.
     *
     * @param target l'eroe da colpire
     * @return {@code true} se ha provato l'attacco mentale, {@code false} se attacca normalmente
     */
    @Override
    public boolean specialMove(Adventurer target) {
        if (isInSecondPhase() && Dice.roll(1, 3) == 1){
            if (Dice.roll(1, 10) <= 7){
                target.takeDamage(target.getMaxHealthPoints() * 30 / 100);
            }
            return true;
        }
        return false;
    }

    /**
     * @return {@link Affinity#PROTECTION}: le armi di protezione gli fanno il 30% di danno in più
     */
    @Override
    public Affinity getWeakness() {
        return Affinity.PROTECTION;
    }
}