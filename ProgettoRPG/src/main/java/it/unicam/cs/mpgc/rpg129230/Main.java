package it.unicam.cs.mpgc.rpg129230;

import it.unicam.cs.mpgc.rpg129230.view.RpgApp;
import javafx.application.Application;

/**
 * Punto di avvio del programma: fa partire la finestra JavaFX del gioco.
 */
public class Main {

    /**
     * Avvia l'applicazione grafica.
     *
     * @param args argomenti da riga di comando (non usati)
     */
    public static void main(String[] args) {
        Application.launch(RpgApp.class, args);
    }
}