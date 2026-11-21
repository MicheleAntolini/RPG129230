package it.unicam.cs.mpgc.rpg129230.view;

import it.unicam.cs.mpgc.rpg129230.Game;
import it.unicam.cs.mpgc.rpg129230.goodentity.Adventurer;
import it.unicam.cs.mpgc.rpg129230.goodentity.Knight;
import it.unicam.cs.mpgc.rpg129230.goodentity.Mage;
import javafx.fxml.FXML;

public class MainMenuController {

    @FXML
    private void onKnight() {
        startGame(new Knight());
    }

    @FXML
    private void onMage() {
        startGame(new Mage());
    }

    private void startGame(Adventurer hero) {
        RpgApp.setGame(new Game(hero));
        RpgApp.showScene("Doors.fxml");
    }
}
