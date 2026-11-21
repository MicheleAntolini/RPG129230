package it.unicam.cs.mpgc.rpg129230.view;

import it.unicam.cs.mpgc.rpg129230.goodentity.Adventurer;
import it.unicam.cs.mpgc.rpg129230.item.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;

/**
 * Controller della schermata dell'inventario (Inventory.fxml).
 * <p>
 * Mostra gli oggetti equipaggiati, le statistiche totali dell'eroe e il contenuto
 * dello zaino; il giocatore seleziona un oggetto dallo zaino e lo equipaggia.
 */
public class InventoryController {

    @FXML private Label equippedLabel;
    @FXML private ListView<String> backpackList;

    private Adventurer hero;
    private List<Item> backpack;   // gli oggetti, nello stesso ordine della lista a schermo

    /**
     * Chiamato da JavaFX appena la schermata è caricata.
     */
    @FXML
    private void initialize() {
        hero = RpgApp.getGame().getHero();
        refresh();
    }

    /**
     * Aggiorna la parte con gli oggetti equipaggiati e la lista dello zaino.
     */
    private void refresh() {
        Inventory inv = hero.getInventory();

        // cosa è equipaggiato
        String text = "Weapon: " + describe(inv.getWeapon())
                + "\nRing: " + describe(inv.getRing())
                + "\nNecklace: " + describe(inv.getNecklace())
                + "\nPotions: " + inv.getPotionSlot().size() + "/2"
                + "\n\n" + hero.getName() + " - HP " + hero.getHealthPoints() + "/" + hero.getMaxHealthPoints()
                + " - ATK " + hero.getTotalAttack() + " - DEF " + hero.getTotalDefense();
        equippedLabel.setText(text);

        // lo zaino
        backpack = inv.getBackpack();
        backpackList.getItems().clear();
        for (Item item : backpack) {
            backpackList.getItems().add(describe(item));
        }
    }

    /**
     * Trasforma un oggetto in una scritta da mostrare, con il suo bonus.
     *
     * @param item l'oggetto (può essere {@code null})
     * @return la descrizione, oppure "none" se l'oggetto è {@code null}
     */
    private String describe(Item item) {
        if (item == null) {
            return "none";
        }
        String text = item.getName() + " (lv. " + item.getLevel() + ")";
        if (item instanceof Weapon w) {
            text += " +" + w.getBonusDamage() + " ATK";
        } else if (item instanceof Ring r) {
            text += " +" + r.getBonusDefense() + " DEF";
        } else if (item instanceof Necklace n) {
            text += " +" + n.getHealthBoost() + " HP max";
        } else if (item instanceof Potion p) {
            text += " heals " + p.getHealthRestore() + " HP";
        }
        return text;
    }

    /**
     * Bottone "Equip": equipaggia l'oggetto selezionato nello zaino, usando il metodo
     * giusto in base al tipo di oggetto.
     */
    @FXML
    private void onEquip() {
        int index = backpackList.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            showMessage("Select an item first.");
            return;
        }

        Item item = backpack.get(index);
        Inventory inv = hero.getInventory();

        if (item instanceof Weapon w) {
            if (!hero.canEquip(w)) {
                showMessage("Your class can't use this weapon.");
                return;
            }
            hero.equipWeapon(w);
        } else if (item instanceof Ring r) {
            inv.equipRing(r);
        } else if (item instanceof Necklace n) {
            hero.equipNecklace(n);
        } else if (item instanceof Potion p) {
            if (!inv.equipPotion(p)) {
                showMessage("Your potion slots are full.");
                return;
            }
        }
        refresh();
    }

    /**
     * Bottone "Back": torna alla schermata delle porte.
     */
    @FXML
    private void onBack() {
        RpgApp.showScene("Doors.fxml");
    }

    /**
     * Mostra un messaggio in una finestrella.
     *
     * @param text il testo del messaggio
     */
    private void showMessage(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, text);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}