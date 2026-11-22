package it.unicam.cs.mpgc.rpg129230.bossentity;

import it.unicam.cs.mpgc.rpg129230.enums.Affinity;
import it.unicam.cs.mpgc.rpg129230.goodentity.Adventurer;
import it.unicam.cs.mpgc.rpg129230.Dice;

/**
 * Demone, uno dei tre boss. È debole alle armi benedette.
 * <p>
 * Mossa speciale: in seconda fase spende un turno per attivare una barriera oscura
 * che dimezza tutti i danni che riceve fino alla fine della battaglia.
 * Le armi benedette oltrepassano la barriera.
 */
public class Demon extends Boss {

    private boolean barrierActive = false;

    /**
     * Crea un Demone con statistiche precise (utile per i test).
     *
     * @param name             il nome
     * @param maxHealthPoints  la vita massima
     * @param attackPower      l'attacco
     * @param defensePower     la difesa
     * @param experienceReward l'exp che regala
     */
    public Demon(String name, int maxHealthPoints, int attackPower, int defensePower, int experienceReward) {
        super(name, maxHealthPoints, attackPower, defensePower, experienceReward);
    }

    /**
     * Crea un Demone con le statistiche del gioco. Non dà exp, perché dopo il boss
     * la partita finisce.
     */
    public Demon(){
        super("Demon", 350, Dice.roll(55, 75), Dice.roll(45, 60), 0);
    }

    /**
     * Attiva la barriera oscura, una volta sola, quando entra in seconda fase.
     *
     * @param target l'eroe (non usato: la barriera agisce sul Demone stesso)
     * @return {@code true} nel turno in cui attiva la barriera, {@code false} negli altri
     */
    @Override
    public boolean specialMove(Adventurer target) {
        if (isInSecondPhase() && !barrierActive) {
            barrierActive = true;
            return true;
        }
        return false;
    }

    /**
     * Se la barriera è attiva dimezza il danno ricevuto, tranne contro le armi benedette.
     * Poi passa il danno a {@link it.unicam.cs.mpgc.rpg129230.badentity.Enemy#takeDamage(int, Affinity)},
     * che applica l'eventuale bonus della debolezza.
     *
     * @param damage         il danno del colpo
     * @param attackAffinity l'affinità dell'arma dell'eroe
     */
    @Override
    public void takeDamage(int damage, Affinity attackAffinity) {
        if (barrierActive && attackAffinity != getWeakness()) {
            damage = damage / 2; // la barriera dimezza il danno
        }
        super.takeDamage(damage, attackAffinity);
    }

    /**
     * @return {@link Affinity#BLESSED}: le armi benedette gli fanno +30% e superano la barriera
     */
    @Override
    public Affinity getWeakness() {
        return Affinity.BLESSED;
    }
}