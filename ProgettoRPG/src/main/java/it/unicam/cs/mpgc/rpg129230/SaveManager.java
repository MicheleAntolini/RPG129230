package it.unicam.cs.mpgc.rpg129230;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveManager {

    private static final Path SAVE_FILE = Path.of("savegame.dat");

    /** Scrive l'intera partita nel file. */
    public static void save(Game game) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(SAVE_FILE))) {
            out.writeObject(game);
        }
    }

    /** Rilegge la partita dal file. */
    public static Game load() throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(SAVE_FILE))) {
            return (Game) in.readObject();
        }
    }
}
