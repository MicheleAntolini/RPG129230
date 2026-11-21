package it.unicam.cs.mpgc.rpg129230.item;

import it.unicam.cs.mpgc.rpg129230.goodentity.Adventurer;

import java.io.Serializable;

/**
 * Qualsiasi oggetto che l'eroe può trovare e tenere nell'inventario.
 * <p>
 * Contiene ciò che hanno tutti gli oggetti: un nome e un livello da 1 a 3.
 * Il controllo sul livello sta nel costruttore, così un oggetto con un livello
 * sbagliato non può proprio essere creato.
 */
public abstract class Item implements Serializable {

    private final String name;
    private final int level;

    /**
     * Crea un oggetto.
     *
     * @param name  il nome dell'oggetto
     * @param level il livello, da 1 a 3
     * @throws IllegalArgumentException se il livello non è tra 1 e 3
     */
    protected Item(String name, int level) {

        if (level < 1 || level > 3) {
            throw new IllegalArgumentException("Item level must be between 1 and 3.");
        }

        this.name = name;
        this.level = level;
    }

    /**
     * @return il nome dell'oggetto
     */
    public String getName() {
        return name;
    }

    /**
     * @return il livello dell'oggetto (1, 2 o 3)
     */
    public int getLevel() {
        return level;
    }

    /**
     * Equipaggia questo oggetto sull'eroe, nel modo giusto per il suo tipo.
     *
     * @param hero l'eroe che lo equipaggia
     * @return {@code true} se l'oggetto è stato equipaggiato, {@code false} se non è possibile
     */
    public abstract boolean equipOn(Adventurer hero);

    /**
     * @return una breve descrizione del bonus dell'oggetto, da mostrare nell'inventario
     */
    public abstract String getBonusDescription();
}