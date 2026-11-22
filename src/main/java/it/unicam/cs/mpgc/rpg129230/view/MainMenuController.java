package it.unicam.cs.mpgc.rpg129230.view;

import it.unicam.cs.mpgc.rpg129230.Game;
import it.unicam.cs.mpgc.rpg129230.goodentity.Adventurer;
import it.unicam.cs.mpgc.rpg129230.goodentity.Knight;
import it.unicam.cs.mpgc.rpg129230.goodentity.Mage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Controller della schermata del menu principale (MainMenu.fxml):
 * il giocatore sceglie il nome e la classe dell'eroe per una nuova partita,
 * può caricare un salvataggio oppure aprire la schermata "How to play".
 */
public class MainMenuController {

    @FXML private TextField nameField;


    /**
     * Bottone "Knight": inizia una partita con il Cavaliere, se il giocatore ha scritto un nome.
     */
    @FXML
    private void onKnight() {
        String name = readName();
        if (name != null) {
            startGame(new Knight(name));
        }
    }

    /**
     * Bottone "Mage": inizia una partita con il Mago, se il giocatore ha scritto un nome.
     */
    @FXML
    private void onMage() {
        String name = readName();
        if (name != null) {
            startGame(new Mage(name));
        }
    }
    private String chosenName(String defaultName) {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            return defaultName;
        }
        return name;
    }

    /**
     * Legge il nome scritto dal giocatore. Se il campo è vuoto mostra un avviso.
     *
     * @return il nome dell'eroe, oppure {@code null} se il giocatore non l'ha scritto
     */
    private String readName() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter your hero's name first.");
            alert.setHeaderText(null);
            RpgApp.styleDialog(alert);
            alert.showAndWait();
            return null;
        }
        return name;
    }

    /**
     * Crea una nuova partita con l'eroe scelto e passa alla schermata delle porte.
     *
     * @param hero l'eroe scelto
     */
    private void startGame(Adventurer hero) {
        RpgApp.setGame(new Game(hero));
        RpgApp.showScene("Doors.fxml");
    }

    /**
     * Bottone "Load game": carica la partita salvata, oppure avvisa se non ce n'è una.
     */
    /**
     * Bottone "Load game": carica la partita salvata, oppure avvisa se non ce n'è una.
     */
    @FXML
    private void onLoad() {
        try {
            RpgApp.setGame(RpgApp.getRepository().load());   // <-- prima era SaveManager.load()
            RpgApp.showScene("Doors.fxml");
        } catch (IOException e) {                            // <-- prima c'era anche ClassNotFoundException
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No saved game found.");
            alert.setHeaderText(null);
            RpgApp.styleDialog(alert);
            alert.showAndWait();
        }
    }

    /**
     * Bottone "How to play": apre la schermata con le regole del gioco.
     */
    @FXML
    private void onHowToPlay() {
        RpgApp.showScene("HowToPlay.fxml");
    }
}