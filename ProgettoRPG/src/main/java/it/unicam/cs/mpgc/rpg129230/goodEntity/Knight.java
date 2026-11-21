package it.unicam.cs.mpgc.rpg129230.goodentity;

import it.unicam.cs.mpgc.rpg129230.Dice;
import it.unicam.cs.mpgc.rpg129230.item.Sword;
import it.unicam.cs.mpgc.rpg129230.item.Weapon;

/**
 * Cavaliere: personaggio fisico con tanta vita e difesa, ma attacco basso.
 * Può usare solo le spade.
 */
public class Knight extends Adventurer {

    /**
     * Crea un Cavaliere per una nuova partita con il nome scelto dal giocatore:
     * statistiche casuali, livello 1 e una spada base già equipaggiata.
     *
     * @param name il nome scelto dal giocatore
     */
    public Knight(String name) {
        super(name, Dice.roll(110, 160), Dice.roll(8, 13), Dice.roll(20, 28), 1, 0);
        Weapon sword = new Sword("Sword", 1, null);
        getInventory().addItem(sword);
        equipWeapon(sword);
    }

    /**
     * @param weapon l'arma da controllare
     * @return {@code true} solo se l'arma è una spada
     */
    @Override
    public boolean canEquip(Weapon weapon) {
        return weapon instanceof Sword;
    }

}