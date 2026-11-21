package it.unicam.cs.mpgc.rpg129230.view;

import it.unicam.cs.mpgc.rpg129230.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UncheckedIOException;

public class RpgApp extends Application {

    private static Stage stage;   // la finestra
    private static Game game;     // la partita in corso

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("RPG");
        showScene("MainMenu.fxml");
        stage.show();
    }

    /** Carica un file .fxml e lo mostra nella finestra. */
    public static void showScene(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(RpgApp.class.getResource(fxml));
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            throw new UncheckedIOException("Impossibile caricare " + fxml, e);
        }
    }

    public static Game getGame() {
        return game;
    }

    public static void setGame(Game newGame) {
        game = newGame;
    }
}
