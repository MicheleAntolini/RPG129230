package it.unicam.cs.mpgc.rpg129230.enums;

/**
 * Le affinità degli oggetti equipaggiabili (armi, anelli, collane).
 * <p>
 * Lo stesso enum indica anche la debolezza di alcuni nemici e dei boss:
 * un'arma con l'affinità giusta fa il 30% di danno in più, e un anello con l'affinità
 * giusta riduce del 25% il danno del boss corrispondente.
 */
public enum Affinity {

    /** Vitale: efficace contro il Lich. */
    VITAL,
    /** Benedetta: efficace contro il Demone e il Vampiro. */
    BLESSED,
    /** Protezione: efficace contro l'Angelo Caduto e il Lupo Mannaro. */
    PROTECTION
}