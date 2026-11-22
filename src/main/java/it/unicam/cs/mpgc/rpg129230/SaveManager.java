package it.unicam.cs.mpgc.rpg129230;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Salva e carica la partita su file.
 * <p>
 * Usa la serializzazione di Java: l'intero oggetto {@link Game} (con eroe, inventario,
 * dungeon...) viene scritto nel file così com'è e poi riletto. Per questo tutte le
 * classi del model implementano {@link java.io.Serializable}.
 */
public class SaveManager {

    private static final Path SAVE_FILE = Path.of("savegame.dat");

    /**
     * Scrive l'intera partita nel file di salvataggio, sovrascrivendo quello vecchio.
     *
     * @param game la partita da salvare
     * @throws IOException se il file non si può scrivere
     */
    public static void save(Game game) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(SAVE_FILE))) {
            out.writeObject(game);
        }
    }

    /**
     * Rilegge la partita dal file di salvataggio.
     *
     * @return la partita salvata
     * @throws IOException            se il file non esiste o non si può leggere
     * @throws ClassNotFoundException se il file contiene dati non riconosciuti
     */
    public static Game load() throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(SAVE_FILE))) {
            return (Game) in.readObject();
        }
    }
}