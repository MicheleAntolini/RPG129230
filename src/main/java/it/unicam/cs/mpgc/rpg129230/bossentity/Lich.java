package it.unicam.cs.mpgc.rpg129230.bossentity;

import it.unicam.cs.mpgc.rpg129230.enums.Affinity;
import it.unicam.cs.mpgc.rpg129230.goodentity.Adventurer;
import it.unicam.cs.mpgc.rpg129230.Dice;

/**
 * Lich, uno dei tre boss. È debole alle armi vitali.
 * <p>
 * Mossa speciale (passiva): in seconda fase lancia una maledizione che, un turno sì
 * e uno no, toglie all'eroe il 10% della vita massima. Non gli costa il turno:
 * subito dopo attacca comunque.
 */
public class Lich extends Boss {

    private int countTurn = 0;   // turni del Lich, per sapere quando scatta la maledizione

    /**
     * Crea un Lich con statistiche precise (utile per i test).
     *
     * @param name             il nome
     * @param maxHealthPoints  la vita massima
     * @param attackPower      l'attacco
     * @param defensePower     la difesa
     * @param experienceReward l'exp che regala
     */
    public Lich(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    /**
     * Crea un Lich con le statistiche del gioco. Non dà exp, perché dopo il boss
     * la partita finisce.
     */
    public Lich() {
        super("Lich", 350, Dice.roll(35, 45), Dice.roll(45, 60), 0);
    }

    /**
     * Conta i turni e, in seconda fase, nei turni pari applica la maledizione.
     *
     * @param target l'eroe maledetto
     * @return sempre {@code false}: la maledizione è passiva, quindi il Lich attacca comunque
     */
    @Override
    public boolean specialMove(Adventurer target) {
        countTurn++;
        if (isInSecondPhase() && countTurn % 2 == 0){
            target.takeDamage(target.getMaxHealthPoints() * 10 / 100);
        }
        return false;
    }

    /**
     * @return {@link Affinity#VITAL}: le armi vitali gli fanno il 30% di danno in più
     */
    @Override
    public Affinity getWeakness() {
        return Affinity.VITAL;
    }

}