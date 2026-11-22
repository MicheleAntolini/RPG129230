package it.unicam.cs.mpgc.rpg129230.view;

import it.unicam.cs.mpgc.rpg129230.Game;
import it.unicam.cs.mpgc.rpg129230.dungeon.Door;
import it.unicam.cs.mpgc.rpg129230.dungeon.Level;
import it.unicam.cs.mpgc.rpg129230.enums.RoomType;
import it.unicam.cs.mpgc.rpg129230.goodentity.Adventurer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.List;

/**
 * Controller della schermata delle porte (Doors.fxml), il "centro" del gioco.
 * <p>
 * Mostra livello, stanza e statistiche dell'eroe, e le 3 porte con le loro probabilità.
 * Da qui si apre una porta, si affronta il boss, si va all'inventario o si salva.
 */
public class DoorsController {

    @FXML private Label levelLabel;
    @FXML private Label heroLabel;
    @FXML private Button door0Button;
    @FXML private Button door1Button;
    @FXML private Button door2Button;
    @FXML private Button bossButton;

    private Game game;

    /**
     * Chiamato da JavaFX appena la schermata è caricata: prende la partita e aggiorna le scritte.
     */
    @FXML
    private void initialize() {
        game = RpgApp.getGame();
        refresh();
    }

    /**
     * Aggiorna tutte le scritte della schermata. Se è il momento del boss
     * nasconde le porte e mostra il bottone del boss.
     */
    private void refresh() {
        Adventurer hero = game.getHero();
        heroLabel.setText(hero.getName() + " - Level " + hero.getLevel()
                + " - HP " + hero.getHealthPoints() + "/" + hero.getMaxHealthPoints()
                + " - EXP " + hero.getExperience() + "/" + hero.getXpToLevelUp());

        Button[] doorButtons = {door0Button, door1Button, door2Button};

        if (game.isBossTime()) {
            levelLabel.setText("The boss is waiting for you!");
            for (Button b : doorButtons) {
                b.setVisible(false);
            }
            bossButton.setVisible(true);
            return;
        }

        bossButton.setVisible(false);
        Level level = game.getDungeon().getCurrentLevel();
        levelLabel.setText("Level " + level.getNumber() + " - Room " + (level.getCurrentStep() + 1) + " of 4");

        List<Door> doors = level.getDoors();
        for (int i = 0; i < doorButtons.length; i++) {
            Door d = doors.get(i);
            doorButtons[i].setText("Door " + (i + 1)
                    + "\nEnemy " + d.getEnemy() + "%"
                    + "\nChest " + d.getTreasure() + "%"
                    + "\nFountain " + d.getFountain() + "%");
        }
    }

    /** Bottone della prima porta. */
    @FXML private void onDoor0() { openDoor(0); }

    /** Bottone della seconda porta. */
    @FXML private void onDoor1() { openDoor(1); }

    /** Bottone della terza porta. */
    @FXML private void onDoor2() { openDoor(2); }

    /**
     * Apre la porta scelta e, in base alla stanza, passa alla battaglia, alla fontana
     * oppure mostra l'oggetto trovato nello scrigno.
     *
     * @param index la porta scelta (0, 1 o 2)
     */
    private void openDoor(int index) {
        RoomType room = game.enterRoom(index);
        switch (room) {
            case ENEMY -> RpgApp.showScene("Battle.fxml");
            case FOUNTAIN -> RpgApp.showScene("Fountain.fxml");
            case TREASURE -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "You found: " + game.getLastFoundItem().getName());
                alert.setHeaderText("Treasure chest!");
                RpgApp.styleDialog(alert);
                alert.showAndWait();
                refresh();
            }
        }
    }

    /**
     * Bottone "Face the boss": fa partire la battaglia finale.
     */
    @FXML
    private void onBoss() {
        game.startBossBattle();
        RpgApp.showScene("Battle.fxml");
    }

    /**
     * Bottone "Inventory": apre la schermata dell'inventario.
     */
    @FXML
    private void onInventory() {
        RpgApp.showScene("Inventory.fxml");
    }

    /**
     * Bottone "Save": salva la partita e mostra un messaggio con l'esito.
     */
    @FXML
    private void onSave() {
        String message;
        try {
            RpgApp.getRepository().save(game);   // <-- prima era SaveManager.save(game);
            message = "Game saved!";
        } catch (IOException e) {
            message = "Could not save the game: " + e.getMessage();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.setHeaderText(null);
        RpgApp.styleDialog(alert);
        alert.showAndWait();
    }
}