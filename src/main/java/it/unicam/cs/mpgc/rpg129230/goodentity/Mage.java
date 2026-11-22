package it.unicam.cs.mpgc.rpg129230.goodentity;

import it.unicam.cs.mpgc.rpg129230.item.Wand;
import it.unicam.cs.mpgc.rpg129230.item.Weapon;
import it.unicam.cs.mpgc.rpg129230.Dice;

/**
 * Mago: personaggio magico con attacco alto, ma poca vita e poca difesa.
 * Può usare solo le bacchette.
 */
public class Mage extends Adventurer {


    /**
     * Crea un Mago per una nuova partita con il nome scelto dal giocatore:
     * statistiche casuali, livello 1 e una bacchetta base già equipaggiata.
     *
     * @param name il nome scelto dal giocatore
     */
    public Mage(String name) {
        super(name, Dice.roll(70, 100), Dice.roll(25, 32), Dice.roll(10, 15), 1, 0);
        Weapon wand = new Wand("Wand", 1, null);
        getInventory().addItem(wand);
        equipWeapon(wand);
    }

    /**
     * @param weapon l'arma da controllare
     * @return {@code true} solo se l'arma è una bacchetta
     */
    @Override
    public boolean canEquip(Weapon weapon) {
        return weapon instanceof Wand;
    }
}