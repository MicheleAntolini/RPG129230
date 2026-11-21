package it.unicam.cs.mpgc.rpg129230.view;

import it.unicam.cs.mpgc.rpg129230.Game;
import it.unicam.cs.mpgc.rpg129230.badentity.Enemy;
import it.unicam.cs.mpgc.rpg129230.dungeon.Battle;
import it.unicam.cs.mpgc.rpg129230.enums.AttackType;
import it.unicam.cs.mpgc.rpg129230.enums.BattleAction;
import it.unicam.cs.mpgc.rpg129230.goodentity.Adventurer;
import it.unicam.cs.mpgc.rpg129230.item.Potion;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BattleController {

    @FXML private Label heroLabel;
    @FXML private Label enemyLabel;
    @FXML private TextArea logArea;
    @FXML private Button attackButton;
    @FXML private Button defendButton;
    @FXML private Button dodgeButton;
    @FXML private Button potionButton;
    @FXML private Button continueButton;

    private Game game;
    private Battle battle;

    @FXML
    private void initialize() {
        game = RpgApp.getGame();
        battle = game.getCurrentBattle();
        log("An enemy " + battle.getEnemy().getName() + " appears!");

        if (!battle.isAdventurerTurn()) {
            log(battle.getEnemy().getName() + " is faster and acts first.");
            enemyTurn();
        }
        refresh();
    }

    // ---------- buttons ----------

    @FXML private void onAttack() { playerTurn(BattleAction.ATTACK, AttackType.BASIC, null); }
    @FXML private void onDefend() { playerTurn(BattleAction.DEFEND, null, null); }
    @FXML private void onDodge()  { playerTurn(BattleAction.DODGE, null, null); }

    @FXML
    private void onPotion() {
        List<Potion> potions = game.getHero().getInventory().getPotionSlot();
        if (potions.isEmpty()) {
            return;
        }

        List<String> choices = new ArrayList<>();
        for (int i = 0; i < potions.size(); i++) {
            Potion p = potions.get(i);
            choices.add((i + 1) + ") " + p.getName() + " lv. " + p.getLevel() + " (+" + p.getHealthRestore() + " HP)");
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setHeaderText("Which potion do you want to use?");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            Potion chosen = potions.get(choices.indexOf(result.get()));
            playerTurn(BattleAction.USE_ITEM, null, chosen);
        }
    }

    @FXML
    private void onContinue() {
        game.finishBattle();

        if (game.isGameOver()) {
            showMessage("Game over", "You have been defeated. Back to the main menu.");
            RpgApp.showScene("MainMenu.fxml");
        } else if (game.isVictory()) {
            showMessage("Victory!", "You defeated the boss and cleared the dungeon!");
            RpgApp.showScene("MainMenu.fxml");
        } else {
            RpgApp.showScene("Doors.fxml");
        }
    }

    // ---------- turns ----------

    private void playerTurn(BattleAction action, AttackType attackType, Potion potion) {
        Adventurer hero = game.getHero();
        Enemy enemy = battle.getEnemy();
        int heroHpBefore = hero.getHealthPoints();
        int enemyHpBefore = enemy.getHealthPoints();

        battle.playerTurn(action, attackType, potion);

        switch (action) {
            case ATTACK -> {
                int damage = enemyHpBefore - enemy.getHealthPoints();
                if (damage > 0) {
                    log("You hit " + enemy.getName() + " for " + damage + " damage.");
                } else {
                    log("Your attack misses!");
                }
            }
            case DEFEND -> log("You raise your guard.");
            case DODGE -> log("You get ready to dodge.");
            case USE_ITEM -> log("You drink a potion and recover " + (hero.getHealthPoints() - heroHpBefore) + " HP.");
        }

        if (!battle.isBattleOver()) {
            enemyTurn();
        }
        refresh();
    }

    private void enemyTurn() {
        Adventurer hero = game.getHero();
        Enemy enemy = battle.getEnemy();
        int heroHpBefore = hero.getHealthPoints();

        battle.enemyTurn();

        int damage = heroHpBefore - hero.getHealthPoints();
        if (damage > 0) {
            log(enemy.getName() + " deals " + damage + " damage to you.");
        } else {
            log(enemy.getName() + " acts, but deals no damage.");
        }
    }

    // ---------- screen update ----------

    private void refresh() {
        Adventurer hero = game.getHero();
        Enemy enemy = battle.getEnemy();

        heroLabel.setText(hero.getName() + "   HP " + hero.getHealthPoints() + "/" + hero.getMaxHealthPoints());
        enemyLabel.setText(enemy.getName() + "   HP " + enemy.getHealthPoints() + "/" + enemy.getMaxHealthPoints());

        boolean over = battle.isBattleOver();
        attackButton.setDisable(over);
        defendButton.setDisable(over);
        dodgeButton.setDisable(over);
        potionButton.setDisable(over || hero.getInventory().getPotionSlot().isEmpty());
        continueButton.setVisible(over);

        if (over) {
            if (hero.isAlive()) {
                log("You defeated " + enemy.getName() + "!");
            } else {
                log("You have been defeated...");
            }
        }
    }

    private void log(String message) {
        logArea.appendText(message + "\n");
    }

    private void showMessage(String header, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, text);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}
