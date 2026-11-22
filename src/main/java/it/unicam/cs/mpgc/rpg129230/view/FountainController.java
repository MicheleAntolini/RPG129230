package it.unicam.cs.mpgc.rpg129230.view;

import it.unicam.cs.mpgc.rpg129230.enums.Stat;
import javafx.fxml.FXML;

/**
 * Controller della schermata della fontana (Fountain.fxml):
 * il giocatore sceglie quale statistica potenziare.
 */
public class FountainController {

    /** Bottone "Health": potenzia la vita. */
    @FXML private void onHealth()  { use(Stat.HEALTH); }

    /** Bottone "Attack": potenzia l'attacco. */
    @FXML private void onAttack()  { use(Stat.ATTACK); }

    /** Bottone "Defense": potenzia la difesa. */
    @FXML private void onDefense() { use(Stat.DEFENSE); }

    /**
     * Applica la fontana alla statistica scelta e torna alla schermata delle porte.
     *
     * @param stat la statistica scelta
     */
    private void use(Stat stat) {
        RpgApp.getGame().useFountain(stat);
        RpgApp.showScene("Doors.fxml");
    }
}