package it.unicam.cs.mpgc.rpg129230.view;

import it.unicam.cs.mpgc.rpg129230.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * L'applicazione JavaFX: gestisce la finestra del gioco e il passaggio da una schermata all'altra.
 * <p>
 * Tiene anche la partita in corso, così tutti i controller possono recuperarla
 * con {@link #getGame()}.
 */
public class RpgApp extends Application {

    private static Stage stage;   // la finestra
    private static Game game;     // la partita in corso

    /**
     * Chiamato da JavaFX all'avvio: apre la finestra con il menu principale.
     *
     * @param primaryStage la finestra creata da JavaFX
     */
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("RPG");
        showScene("MainMenu.fxml");
        stage.show();
    }

    /**
     * Carica una schermata creata con SceneBuilder e la mostra nella finestra.
     *
     * @param fxml il nome del file .fxml (nella cartella resources/.../view)
     * @throws UncheckedIOException se il file non esiste o contiene errori
     */
    public static void showScene(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(RpgApp.class.getResource(fxml));
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            throw new UncheckedIOException("Impossibile caricare " + fxml, e);
        }
    }

    /**
     * @return la partita in corso
     */
    public static Game getGame() {
        return game;
    }

    /**
     * Imposta la partita in corso (nuova partita o partita caricata).
     *
     * @param newGame la partita
     */
    public static void setGame(Game newGame) {
        game = newGame;
    }
}