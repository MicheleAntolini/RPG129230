package it.unicam.cs.mpgc.rpg129230.enums;

/**
 * I tipi di attacco che l'eroe può scegliere quando attacca.
 */
public enum AttackType {

    /** Attacco normale: colpisce sempre (salvo schivata del nemico) e può attivare la collana. */
    BASIC,
    /** Attacco di potenza: più danno ma meno preciso, con ricarica. */
    POWER,
    /** Stun: poco danno, ma se va a segno il nemico salta dei turni. Ha una ricarica. */
    IMMOBILIZING
}