package it.unicam.cs.mpgc.rpg129230.saves;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import it.unicam.cs.mpgc.rpg129230.Game;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Salva e carica la partita in un file di testo in formato JSON, usando la libreria Gson.
 * <p>
 * A differenza della serializzazione di Java, il file si può aprire e leggere, e potrebbe
 * essere letto anche da programmi non scritti in Java (per esempio una versione web del gioco).
 */
public class JsonGameRepository implements GameRepository {

    private final Path saveFile;   // il file dove si salva
    private final Gson gson;       // l'oggetto di Gson che trasforma la partita in JSON e viceversa

    /**
     * Crea un salvataggio in formato JSON sul file indicato.
     *
     * @param saveFile il file da usare
     */
    public JsonGameRepository(Path saveFile) {
        this.saveFile = saveFile;
        this.gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ClassNameAdapterFactory())  // ricorda le classi vere
                .setPrettyPrinting()                                         // JSON ordinato e leggibile
                .create();
    }

    @Override
    public void save(Game game) throws IOException {
        try (Writer writer = Files.newBufferedWriter(saveFile, StandardCharsets.UTF_8)) {
            gson.toJson(game, writer);
        }
    }

    @Override
    public Game load() throws IOException {
        try (Reader reader = Files.newBufferedReader(saveFile, StandardCharsets.UTF_8)) {
            Game game = gson.fromJson(reader, Game.class);
            if (game == null) {
                throw new IOException("Save file is empty");
            }
            return game;
        } catch (JsonParseException e) {
            throw new IOException("Save file is not valid", e);
        }
    }
}