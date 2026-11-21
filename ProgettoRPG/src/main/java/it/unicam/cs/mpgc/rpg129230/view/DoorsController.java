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

import java.util.List;

public class DoorsController {

    @FXML private Label levelLabel;
    @FXML private Label heroLabel;
    @FXML private Button door0Button;
    @FXML private Button door1Button;
    @FXML private Button door2Button;
    @FXML private Button bossButton;

    private Game game;

    @FXML
    private void initialize() {
        game = RpgApp.getGame();
        refresh();
    }

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

    @FXML private void onDoor0() { openDoor(0); }
    @FXML private void onDoor1() { openDoor(1); }
    @FXML private void onDoor2() { openDoor(2); }

    private void openDoor(int index) {
        RoomType room = game.enterRoom(index);
        switch (room) {
            case ENEMY -> RpgApp.showScene("Battle.fxml");
            case FOUNTAIN -> RpgApp.showScene("Fountain.fxml");
            case TREASURE -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "You found: " + game.getLastFoundItem().getName());
                alert.setHeaderText("Treasure chest!");
                alert.showAndWait();
                refresh();
            }
        }
    }

    @FXML
    private void onBoss() {
        game.startBossBattle();
        RpgApp.showScene("Battle.fxml");
    }

    @FXML
    private void onInventory() {
        RpgApp.showScene("Inventory.fxml");
    }
}
