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
    private int immobilizingCooldown;   // turni che mancano per riusare l'immobilizzante
    private int enemyStunnedTurns;      // turni in cui il nemico è ancora bloccato

    public Battle(Adventurer adventurer, Enemy enemy) {
        this.adventurer = adventurer;
        this.enemy = enemy;
        initiative();
    }

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

    /** Tira i dadi per vedere se l'attacco va a segno. */
    private boolean hitsEnemy(int hitChance) {
        // il nemico può schivare, ma non se è immobilizzato
        if (enemyStunnedTurns == 0 && Dice.roll(1, 100) <= enemy.getEvasion()) {
            return false;
        }
        return Dice.roll(1, 100) <= hitChance;
    }

    /** Dopo un attacco base andato a segno: 50% di probabilità che la collana si attivi. */
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

    public void enemyTurn() {

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

        // nemico stordito: salta il turno
        if (enemyStunnedTurns > 0) {
            endEnemyTurn();
            return;
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

    /** Fine del turno del nemico: il blocco scende di un turno e tocca all'eroe. */
    private void endEnemyTurn() {
        if (enemyStunnedTurns > 0) {
            enemyStunnedTurns--;
        }
        adventurerTurn = true;
    }

    // ---------- utilità ----------

    private int calculateDamage(int attack, int defense) {
        return attack * 100 / (100 + defense);
    }

    public boolean isBattleOver() {
        return !adventurer.isAlive() || !enemy.isAlive();
    }

    public void endBattle() {
        if (adventurer.isAlive()) {
            adventurer.gainExperience(enemy);
        }
    }

    // ---------- getter per l'interfaccia ----------

    public boolean isAdventurerTurn() {
        return adventurerTurn;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public boolean isShieldActive() {
        return shield;
    }

    public boolean hasBlessedBonus() {
        return blessedBonus;
    }

    public int getPowerCooldown() {
        return powerCooldown;
    }

    public int getImmobilizingCooldown() {
        return immobilizingCooldown;
    }

    public boolean isEnemyStunned() {
        return enemyStunnedTurns > 0;
    }
}
