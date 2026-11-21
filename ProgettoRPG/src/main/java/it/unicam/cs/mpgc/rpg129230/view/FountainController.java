package it.unicam.cs.mpgc.rpg129230.view;

import it.unicam.cs.mpgc.rpg129230.enums.Stat;
import javafx.fxml.FXML;

public class FountainController {

    @FXML private void onHealth()  { use(Stat.HEALTH); }
    @FXML private void onAttack()  { use(Stat.ATTACK); }
    @FXML private void onDefense() { use(Stat.DEFENSE); }

    private void use(Stat stat) {
        RpgApp.getGame().useFountain(stat);
        RpgApp.showScene("Doors.fxml");
    }
}
