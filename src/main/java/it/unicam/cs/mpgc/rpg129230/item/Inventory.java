package it.unicam.cs.mpgc.rpg129230.item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * L'inventario dell'eroe, diviso in tre zone:
 * <ul>
 *     <li>gli slot equipaggiati: un'arma, un anello e una collana;</li>
 *     <li>lo slot delle pozioni pronte da bere in battaglia (massimo 2);</li>
 *     <li>lo zaino, senza limite, dove finiscono gli oggetti trovati e quelli tolti dagli slot.</li>
 * </ul>
 * Gli oggetti si spostano sempre tra lo zaino e gli slot: niente viene perso.
 */
public class Inventory implements Serializable {

    private Ring ring;
    private Necklace necklace;
    private Weapon weapon;
    private static final int MAX_POTION_SLOT = 2;

    private final List<Item> backpack = new ArrayList<>();
    private final List<Potion> potionSlot = new ArrayList<>();

    /**
     * Equipaggia un anello: quello vecchio (se c'era) torna nello zaino,
     * quello nuovo esce dallo zaino e va nello slot.
     *
     * @param newRing l'anello da equipaggiare
     */
    public void equipRing(Ring newRing) {

        if (this.ring != null){
            backpack.add(this.ring);
        }

        backpack.remove(newRing);

        this.ring = newRing;
    }

    /**
     * Equipaggia una collana, con lo stesso scambio zaino/slot dell'anello.
     * Per aggiornare anche la vita dell'eroe va usato
     * {@link it.unicam.cs.mpgc.rpg129230.goodentity.Adventurer#equipNecklace}.
     *
     * @param newNecklace la collana da equipaggiare
     */
    public void equipNecklace(Necklace newNecklace){

        if (this.necklace != null){
            backpack.add(this.necklace);
        }

        backpack.remove(newNecklace);

        this.necklace = newNecklace;
    }

    /**
     * Equipaggia un'arma, con lo stesso scambio zaino/slot dell'anello.
     * Il controllo sulla classe dell'eroe lo fa
     * {@link it.unicam.cs.mpgc.rpg129230.goodentity.Adventurer#equipWeapon}.
     *
     * @param newWeapon l'arma da equipaggiare
     */
    public void equipWeapon(Weapon newWeapon){

        if (this.weapon != null){
            backpack.add(this.weapon);
        }

        backpack.remove(newWeapon);

        this.weapon = newWeapon;
    }

    /**
     * Sposta una pozione dallo zaino allo slot delle pozioni, se c'è posto.
     *
     * @param newPotion la pozione da preparare
     * @return {@code true} se la pozione è entrata nello slot, {@code false} se lo slot era pieno
     */
    public boolean equipPotion(Potion newPotion){

        if(potionSlot.size() == MAX_POTION_SLOT){
            return false;
        } else {
            backpack.remove(newPotion);
            potionSlot.add(newPotion);
            return true;
        }
    }

    /**
     * Toglie una pozione dallo slot perché è stata bevuta.
     *
     * @param potion la pozione bevuta
     * @return {@code true} se la pozione era nello slot ed è stata tolta
     */
    public boolean removePotion(Potion potion) {
        return potionSlot.remove(potion);
    }

    /**
     * Mette un oggetto nello zaino (per esempio quando si apre uno scrigno).
     *
     * @param newItem l'oggetto trovato
     */
    public void addItem(Item newItem){
        backpack.add(newItem);
    }

    /**
     * @return l'anello equipaggiato, oppure {@code null}
     */
    public Ring getRing() {
        return ring;
    }

    /**
     * @return la collana equipaggiata, oppure {@code null}
     */
    public Necklace getNecklace() {
        return necklace;
    }

    /**
     * @return l'arma equipaggiata, oppure {@code null}
     */
    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * @return una copia di sola lettura dello zaino: per modificarlo bisogna passare dai metodi
     */
    public List<Item> getBackpack() {
        return List.copyOf(backpack);
    }

    /**
     * @return una copia di sola lettura dello slot delle pozioni
     */
    public List<Potion> getPotionSlot() {
        return List.copyOf(potionSlot);
    }
}