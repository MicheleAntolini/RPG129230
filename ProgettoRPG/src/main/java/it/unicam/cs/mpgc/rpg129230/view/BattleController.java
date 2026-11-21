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

/**
 * Controller della schermata di battaglia (Battle.fxml).
 * <p>
 * Ogni clic su un bottone d'azione corrisponde a un turno completo: prima agisce
 * l'eroe, poi (se la battaglia non è finita) il nemico. Il racconto della battaglia
 * viene scritto nel riquadro di testo confrontando la vita prima e dopo ogni turno.
 * La logica vera e propria sta in {@link Battle}: qui si gestiscono solo bottoni e scritte.
 */
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

    /**
     * Chiamato da JavaFX appena la schermata è caricata. Se il nemico ha vinto
     * l'iniziativa, lo fa agire subito.
     */
    @FXML
    private void initialize() {
        game = RpgApp.getGame();
        battle = game.getCurrentBattle();
        log("A wild " + battle.getEnemy().getName() + " appears!");

        if (!battle.isAdventurerTurn()) {
            log(battle.getEnemy().getName() + " is faster and acts first.");
            enemyTurn();
        }
        refresh();
    }

    // ---------- bottoni ----------

    /**
     * Bottone "Attack": apre una finestrella per scegliere il tipo di attacco.
     * Gli attacchi speciali in ricarica non compaiono tra le scelte.
     */
    @FXML
    private void onAttack() {
        String basic = "Basic attack";
        String power = "Power attack (180% damage, 70% hit)";
        String immobilizing = "Stun attack (50% damage, 60% hit, enemy skips 2 turns)";

        // si possono scegliere solo gli attacchi pronti
        List<String> choices = new ArrayList<>();
        choices.add(basic);
        if (battle.getPowerCooldown() == 0) {
            choices.add(power);
        }
        if (battle.getImmobilizingCooldown() == 0) {
            choices.add(immobilizing);
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(basic, choices);
        dialog.setHeaderText("Choose your attack");
        dialog.setContentText(cooldownText("Power", battle.getPowerCooldown())
                + "   " + cooldownText("Immobilizing", battle.getImmobilizingCooldown()));
        Optional<String> result = dialog.showAndWait();

        if (result.isEmpty()) {
            return; // il giocatore ha premuto Annulla
        }

        String chosen = result.get();
        if (chosen.equals(power)) {
            playerTurn(BattleAction.ATTACK, AttackType.POWER, null);
        } else if (chosen.equals(immobilizing)) {
            playerTurn(BattleAction.ATTACK, AttackType.IMMOBILIZING, null);
        } else {
            playerTurn(BattleAction.ATTACK, AttackType.BASIC, null);
        }
    }

    /** Bottone "Defend": l'eroe si difende. */
    @FXML private void onDefend() { playerTurn(BattleAction.DEFEND, null, null); }

    /** Bottone "Dodge": l'eroe si prepara a schivare. */
    @FXML private void onDodge()  { playerTurn(BattleAction.DODGE, null, null); }

    /**
     * Bottone "Potion": apre una finestrella per scegliere quale pozione bere
     * tra quelle nello slot.
     */
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

    /**
     * Bottone "Continue" (a battaglia finita): chiude la battaglia e decide la schermata
     * successiva, nell'ordine: sconfitta, vittoria sul boss, oppure ritorno alle porte.
     */
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

    // ---------- turni ----------

    /**
     * Esegue un turno completo: azione dell'eroe, racconto di cosa è successo,
     * poi turno del nemico (se la battaglia non è finita) e aggiornamento dello schermo.
     *
     * @param action     l'azione scelta
     * @param attackType il tipo di attacco (solo per ATTACK)
     * @param potion     la pozione scelta (solo per USE_ITEM)
     */
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
                    if (attackType == AttackType.IMMOBILIZING) {
                        log(enemy.getName() + " is stunned!");
                    }
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

    /**
     * Esegue il turno del nemico e scrive nel racconto quanti danni ha fatto.
     */
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

    // ---------- aggiornamento schermo ----------

    /**
     * Aggiorna vite ed effetti attivi; a battaglia finita disattiva le azioni
     * e mostra il bottone "Continue".
     */
    private void refresh() {
        Adventurer hero = game.getHero();
        Enemy enemy = battle.getEnemy();

        String heroStatus = "";
        if (battle.isShieldActive()) {
            heroStatus += "  [Shield]";
        }
        if (battle.hasBlessedBonus()) {
            heroStatus += "  [Blessed +20%]";
        }
        heroLabel.setText(hero.getName() + "   HP " + hero.getHealthPoints() + "/" + hero.getMaxHealthPoints() + heroStatus);

        String enemyStatus = battle.isEnemyStunned() ? "  [Stunned]" : "";
        enemyLabel.setText(enemy.getName() + "   HP " + enemy.getHealthPoints() + "/" + enemy.getMaxHealthPoints() + enemyStatus);

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

    /**
     * Scritta che dice se un attacco speciale è pronto o quanti turni mancano.
     *
     * @param name     il nome dell'attacco
     * @param cooldown i turni di ricarica rimasti
     * @return la scritta da mostrare
     */
    private String cooldownText(String name, int cooldown) {
        if (cooldown == 0) {
            return name + ": ready";
        }
        return name + ": ready in " + cooldown + " turns";
    }

    /**
     * Aggiunge una riga al racconto della battaglia.
     *
     * @param message la riga da aggiungere
     */
    private void log(String message) {
        logArea.appendText(message + "\n");
    }

    /**
     * Mostra un messaggio in una finestrella.
     *
     * @param header il titolo
     * @param text   il testo
     */
    private void showMessage(String header, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, text);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}