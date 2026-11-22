package it.unicam.cs.mpgc.rpg129230.saves;

import it.unicam.cs.mpgc.rpg129230.Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Salva e carica la partita su un file del computer, usando la serializzazione di Java.
 */
public class FileGameRepository implements GameRepository {

    private final Path saveFile;   // il file dove si salva

    /**
     * Crea un salvataggio sul file indicato.
     *
     * @param saveFile il file da usare
     */
    public FileGameRepository(Path saveFile) {
        this.saveFile = saveFile;
    }

    @Override
    public void save(Game game) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(saveFile))) {
            out.writeObject(game);
        }
    }

    @Override
    public Game load() throws IOException {
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(saveFile))) {
            return (Game) in.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Save file is not valid", e);
        }
    }
}