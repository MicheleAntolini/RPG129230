package it.unicam.cs.mpgc.rpg129230.item;

import it.unicam.cs.mpgc.rpg129230.enums.Affinity;
import it.unicam.cs.mpgc.rpg129230.goodentity.Adventurer;

/**
 * Un'arma: aumenta l'attacco dell'eroe.
 * <p>
 * È astratta perché nel gioco esistono solo spade ({@link Sword}) e bacchette ({@link Wand}),
 * che hanno lo stesso comportamento: questa classe evita di scriverlo due volte.
 */
public abstract class Weapon extends EquippableItem {

    /**
     * Crea un'arma.
     *
     * @param name     il nome
     * @param level    il livello, da 1 a 3
     * @param affinity l'affinità, oppure {@code null}
     */
    protected Weapon(String name, int level, Affinity affinity) {
        super(name, level, affinity);
    }

    /**
     * @return il bonus all'attacco: +15 per ogni livello (15, 30, 45)
     */
    public int getBonusDamage() {

        return super.getLevel() * 15;
    }

    /**
     * Equipaggia l'arma, solo se la classe dell'eroe può usarla.
     */
    @Override
    public boolean equipOn(Adventurer hero) {
        if (!hero.canEquip(this)) {
            return false;
        }
        hero.equipWeapon(this);
        return true;
    }

    @Override
    public String getBonusDescription() {
        return " +" + getBonusDamage() + " ATK";
    }
}