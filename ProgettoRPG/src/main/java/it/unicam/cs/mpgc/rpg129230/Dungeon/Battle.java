package it.unicam.cs.mpgc.rpg129230.dungeon;

import it.unicam.cs.mpgc.rpg129230.Dice;
import it.unicam.cs.mpgc.rpg129230.enums.Affinity;
import it.unicam.cs.mpgc.rpg129230.enums.AttackType;
import it.unicam.cs.mpgc.rpg129230.enums.BattleAction;
import it.unicam.cs.mpgc.rpg129230.badentity.Enemy;
import it.unicam.cs.mpgc.rpg129230.bossentity.Boss;
import it.unicam.cs.mpgc.rpg129230.goodentity.Adventurer;
import it.unicam.cs.mpgc.rpg129230.item.Potion;

import java.io.Serializable;

/**
 * Un combattimento a turni tra l'eroe e un singolo nemico (o boss).
 * <p>
 * All'inizio si tira l'iniziativa per decidere chi agisce per primo. Poi i turni si
 * alternano: l'interfaccia chiama {@link #playerTurn} con l'azione scelta dal giocatore
 * e {@link #enemyTurn} per il turno del nemico, finché {@link #isBattleOver} non diventa vero.
 * <p>
 * Questa classe si ricorda anche tutto ciò che dura più di un turno: difesa e schivata
 * dell'eroe, effetti delle collane, ricariche degli attacchi speciali e stordimento del nemico.
 */
public class Battle implements Serializable {

    private static final int POWER_COOLDOWN = 3;
    private static final int IMMOBILIZING_COOLDOWN = 4;
    private static final int STUN_TURNS = 2;

    private final Adventurer adventurer;
    private final Enemy enemy;
    private boolean defense;
    private boolean dodge;
    private boolean adventurerTurn;

    private boolean blessedBonus;       // collana benedetta: il prossimo attacco base fa +20%
    private boolean shield;             // collana di protezione: blocca il prossimo attacco nemico

    private int powerCooldown;          // turni che mancano per riusare l'attacco di potenza
    private int immobilizingCooldown;   // turni che mancano per riusare lo stun
    private int enemyStunnedTurns;      // turni in cui il nemico è ancora stordito

    /**
     * Crea una battaglia e tira subito l'iniziativa.
     *
     * @param adventurer l'eroe
     * @param enemy      il nemico da affrontare
     */
    public Battle(Adventurer adventurer, Enemy enemy) {
        this.adventurer = adventurer;
        this.enemy = enemy;
        initiative();
    }

    /**
     * Tira un dado da 1 a 10 per l'eroe e uno per il nemico, ripetendo in caso di pareggio:
     * chi fa il numero più alto agisce per primo.
     */
    private void initiative() {
        int adventurerRoll;
        int enemyRoll;

        do {
            adventurerRoll = Dice.roll(1, 10);
            enemyRoll = Dice.roll(1, 10);
        } while (adventurerRoll == enemyRoll);

        if (adventurerRoll > enemyRoll) {
            adventurerTurn = true;
        } else {
            adventurerTurn = false;
        }
    }

    // ---------- turno dell'eroe ----------

    /**
     * Esegue il turno dell'eroe con l'azione scelta dal giocatore.
     * All'inizio spegne difesa e schivata del turno prima e fa scendere le ricariche.
     *
     * @param action     l'azione scelta (attacca, difendi, schiva, usa oggetto)
     * @param attackType il tipo di attacco, se l'azione è ATTACK (altrimenti {@code null})
     * @param potion     la pozione da bere, se l'azione è USE_ITEM (altrimenti {@code null})
     */
    public void playerTurn(BattleAction action, AttackType attackType, Potion potion) {

        defense = false;
        dodge = false;

        // le ricariche scendono di un turno
        if (powerCooldown > 0) {
            powerCooldown--;
        }
        if (immobilizingCooldown > 0) {
            immobilizingCooldown--;
        }

        switch (action) {
            case ATTACK -> attack(attackType);
            case DEFEND -> defense = true;
            case DODGE -> dodge = true;
            case USE_ITEM -> {
                if (adventurer.getInventory().removePotion(potion)) {
                    adventurer.heal(potion.getHealthRestore());
                }
            }
        }
        adventurerTurn = false;
    }

    /**
     * Esegue l'attacco scelto.
     * <ul>
     *     <li>BASIC: colpisce sempre (salvo schivata del nemico) e può attivare la collana;</li>
     *     <li>POWER: 180% di danno, 70% di colpire, ricarica di 3 turni;</li>
     *     <li>IMMOBILIZING (stun): 50% di danno, 60% di colpire; se va a segno il nemico
     *     salta 2 turni. Ricarica di 4 turni.</li>
     * </ul>
     * La ricarica parte anche se l'attacco manca.
     *
     * @param attackType il tipo di attacco
     * @throws IllegalStateException se l'attacco speciale scelto è ancora in ricarica
     */
    private void attack(AttackType attackType) {
        switch (attackType) {
            case BASIC -> {
                if (!hitsEnemy(100)) {
                    return;
                }
                int damage = calculateDamage(adventurer.getTotalAttack(), enemy.getDefensePower());
                if (blessedBonus) {
                    damage = damage * 120 / 100;
                    blessedBonus = false;
                }
                enemy.takeDamage(damage, adventurer.getWeaponAffinity());
                applyNecklaceEffect();
            }
            case POWER -> {
                if (powerCooldown > 0) {
                    throw new IllegalStateException("Power attack is not ready");
                }
                powerCooldown = POWER_COOLDOWN;
                if (!hitsEnemy(70)) {
                    return;
                }
                int damage = calculateDamage(adventurer.getTotalAttack(), enemy.getDefensePower()) * 180 / 100;
                enemy.takeDamage(damage, adventurer.getWeaponAffinity());
            }
            case IMMOBILIZING -> {
                if (immobilizingCooldown > 0) {
                    throw new IllegalStateException("Immobilizing attack is not ready");
                }
                immobilizingCooldown = IMMOBILIZING_COOLDOWN;
                if (!hitsEnemy(60)) {
                    return;
                }
                int damage = calculateDamage(adventurer.getTotalAttack(), enemy.getDefensePower()) * 50 / 100;
                enemy.takeDamage(damage, adventurer.getWeaponAffinity());
                enemyStunnedTurns = STUN_TURNS;
            }
        }
    }

    /**
     * Tira i dadi per vedere se un attacco va a segno. Prima il nemico prova a schivare
     * (solo se non è stordito), poi si controlla la precisione dell'attacco.
     *
     * @param hitChance la precisione dell'attacco, in percentuale
     * @return {@code true} se il colpo va a segno
     */
    private boolean hitsEnemy(int hitChance) {
        // il nemico può schivare, ma non se è stordito
        if (enemyStunnedTurns == 0 && Dice.roll(1, 100) <= enemy.getEvasion()) {
            return false;
        }
        return Dice.roll(1, 100) <= hitChance;
    }

    /**
     * Dopo un attacco base andato a segno: con il 50% di probabilità la collana si attiva.
     * Benedetta → +20% al prossimo attacco base; vitale → cura del 5% della vita massima;
     * protezione → scudo che blocca il prossimo attacco nemico.
     */
    private void applyNecklaceEffect() {
        Affinity affinity = adventurer.getNecklaceAffinity();
        if (affinity == null || Dice.roll(1, 2) == 1) {
            return;
        }
        switch (affinity) {
            case BLESSED -> blessedBonus = true;
            case VITAL -> adventurer.heal(adventurer.getMaxHealthPoints() * 5 / 100);
            case PROTECTION -> shield = true;
        }
    }

    // ---------- turno del nemico ----------

    /**
     * Esegue il turno del nemico, controllando nell'ordine:
     * <ol>
     *     <li>se è stordito, salta il turno;</li>
     *     <li>se è un boss, può usare la sua mossa speciale;</li>
     *     <li>se l'eroe ha lo scudo della collana, l'attacco viene bloccato;</li>
     *     <li>altrimenti attacca: il danno si riduce se l'eroe si difende o ha l'anello
     *     giusto contro il boss, e può andare a vuoto se l'eroe schiva.</li>
     * </ol>
     */
    public void enemyTurn() {

        // nemico stordito: salta il turno
        if (enemyStunnedTurns > 0) {
            endEnemyTurn();
            return;
        }

        // se il nemico è un boss e usa la sua mossa, il turno finisce qui
        if (enemy instanceof Boss boss && boss.specialMove(adventurer)) {
            endEnemyTurn();
            return;
        }

        // scudo della collana di protezione: blocca questo attacco
        if (shield) {
            shield = false;
            endEnemyTurn();
            return;
        }

        int damage = calculateDamage(enemy.getAttackPower(), adventurer.getTotalDefense());

        if (defense) {
            damage = damage * 70 / 100;
        }

        if (enemy instanceof Boss boss
                && adventurer.getRingAffinity() != null
                && adventurer.getRingAffinity() == boss.getWeakness()) {
            damage = damage * 75 / 100;
        }

        if (dodge && Dice.roll(1, 10) <= 3) {
            // colpo mancato
        } else {
            adventurer.takeDamage(damage);
        }

        endEnemyTurn();
    }

    /**
     * Fine del turno del nemico: lo stordimento scende di un turno e tocca all'eroe.
     */
    private void endEnemyTurn() {
        if (enemyStunnedTurns > 0) {
            enemyStunnedTurns--;
        }
        adventurerTurn = true;
    }

    // ---------- utilità ----------

    /**
     * Formula del danno: attacco * 100 / (100 + difesa). Più difesa ha il bersaglio,
     * meno danno riceve, ma il danno non arriva mai a zero.
     *
     * @param attack  l'attacco di chi colpisce
     * @param defense la difesa di chi viene colpito
     * @return il danno inflitto
     */
    private int calculateDamage(int attack, int defense) {
        return attack * 100 / (100 + defense);
    }

    /**
     * @return {@code true} se l'eroe o il nemico è morto
     */
    public boolean isBattleOver() {
        return !adventurer.isAlive() || !enemy.isAlive();
    }

    /**
     * Da chiamare una volta sola a battaglia finita: se l'eroe ha vinto, riceve l'exp.
     */
    public void endBattle() {
        if (adventurer.isAlive()) {
            adventurer.gainExperience(enemy);
        }
    }

    // ---------- getter per l'interfaccia ----------

    /**
     * @return {@code true} se tocca all'eroe
     */
    public boolean isAdventurerTurn() {
        return adventurerTurn;
    }

    /**
     * @return il nemico di questa battaglia
     */
    public Enemy getEnemy() {
        return enemy;
    }

    /**
     * @return {@code true} se lo scudo della collana di protezione è attivo
     */
    public boolean isShieldActive() {
        return shield;
    }

    /**
     * @return {@code true} se il prossimo attacco base ha il bonus della collana benedetta
     */
    public boolean hasBlessedBonus() {
        return blessedBonus;
    }

    /**
     * @return i turni che mancano per riusare l'attacco di potenza (0 = pronto)
     */
    public int getPowerCooldown() {
        return powerCooldown;
    }

    /**
     * @return i turni che mancano per riusare lo stun (0 = pronto)
     */
    public int getImmobilizingCooldown() {
        return immobilizingCooldown;
    }

    /**
     * @return {@code true} se il nemico è stordito
     */
    public boolean isEnemyStunned() {
        return enemyStunnedTurns > 0;
    }
}