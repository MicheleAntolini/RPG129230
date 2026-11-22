package it.unicam.cs.mpgc.rpg129230.enums;

/**
 * Le azioni che il giocatore può scegliere nel suo turno di battaglia.
 */
public enum BattleAction {

    /** Attacca il nemico (con uno dei tipi di {@link AttackType}). */
    ATTACK,
    /** Si difende: il prossimo attacco nemico fa il 30% di danno in meno. */
    DEFEND,
    /** Prova a schivare: il prossimo attacco nemico ha il 30% di andare a vuoto. */
    DODGE,
    /** Usa una pozione dallo slot delle pozioni. */
    USE_ITEM
}