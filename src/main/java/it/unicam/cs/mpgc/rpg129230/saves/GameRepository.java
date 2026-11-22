package it.unicam.cs.mpgc.rpg129230.saves;

import it.unicam.cs.mpgc.rpg129230.Game;

import java.io.IOException;

/**
 * Descrive come salvare e caricare una partita, senza dire dove.
 * <p>
 * Il resto del gioco usa solo questa interfaccia: per salvare in un altro modo
 * (su un server, in un database...) basta scrivere una nuova classe che la implementa,
 * senza toccare i controller.
 */
public interface GameRepository {

    /**
     * Salva la partita.
     *
     * @param game la partita da salvare
     * @throws IOException se il salvataggio non riesce
     */
    void save(Game game) throws IOException;

    /**
     * Carica la partita salvata.
     *
     * @return la partita caricata
     * @throws IOException se non c'è un salvataggio o non si riesce a leggerlo
     */
    Game load() throws IOException;
}