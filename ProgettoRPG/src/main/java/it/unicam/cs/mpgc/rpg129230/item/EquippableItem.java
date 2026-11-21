package it.unicam.cs.mpgc.rpg129230.item;

import it.unicam.cs.mpgc.rpg129230.enums.Affinity;

/**
 * Un oggetto che si può equipaggiare: arma, anello o collana.
 * <p>
 * Rispetto a {@link Item} aggiunge l'affinità (vitale, benedetta o protezione).
 * Le pozioni non hanno affinità, per questo estendono direttamente {@link Item}.
 */
public abstract class EquippableItem extends Item{

    private final Affinity affinity;

    /**
     * Crea un oggetto equipaggiabile.
     *
     * @param name     il nome
     * @param level    il livello, da 1 a 3
     * @param affinity l'affinità, oppure {@code null} per un oggetto senza affinità
     */
    protected EquippableItem(String name, int level, Affinity affinity) {
        super(name, level);

        this.affinity = affinity;
    }

    /**
     * @return l'affinità dell'oggetto, oppure {@code null} se non ne ha
     */
    public Affinity getAffinity() {
        return affinity;
    }
}