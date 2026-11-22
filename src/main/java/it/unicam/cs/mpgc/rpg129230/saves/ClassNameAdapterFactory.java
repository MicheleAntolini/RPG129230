package it.unicam.cs.mpgc.rpg129230.saves;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Modifier;

/**
 * Insegna a Gson a salvare e ricaricare gli oggetti di cui si conosce solo la superclasse.
 * <p>
 * Il JSON salva solo i valori, non la classe di un oggetto: nello zaino, per esempio, ci sono
 * degli {@code Item} che in realtà sono {@code Sword}, {@code Ring} o {@code Potion}. Per ogni
 * campo che ha come tipo una classe astratta o un'interfaccia del gioco, questa classe salva
 * anche il nome della classe vera:
 * <pre>
 * { "class": "it.unicam.cs.mpgc.rpg129230.item.Sword", "data": { ...i campi della spada... } }
 * </pre>
 * e al caricamento usa quel nome per ricreare l'oggetto giusto.
 */
public class ClassNameAdapterFactory implements TypeAdapterFactory {

    private static final String GAME_PACKAGE = "it.unicam.cs.mpgc.rpg129230";
    private static final String CLASS_KEY = "class";
    private static final String DATA_KEY = "data";

    /**
     * Chiamato da Gson per ogni tipo che incontra: interviene solo sulle classi astratte
     * e sulle interfacce del gioco, per tutti gli altri tipi lascia fare a Gson.
     */
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<? super T> rawType = type.getRawType();
        if (!needsClassName(rawType)) {
            return null; // null = "non mi riguarda, usa il comportamento normale"
        }
        return new ClassNameAdapter<>(gson, rawType);
    }

    /**
     * @param rawType la classe da controllare
     * @return {@code true} se è una classe astratta o un'interfaccia del gioco
     */
    private boolean needsClassName(Class<?> rawType) {
        boolean ofThisGame = rawType.getName().startsWith(GAME_PACKAGE);
        boolean abstractOrInterface = rawType.isInterface() || Modifier.isAbstract(rawType.getModifiers());
        return ofThisGame && abstractOrInterface && !rawType.isEnum();
    }

    /**
     * Scrive e legge un singolo oggetto insieme al nome della sua classe vera.
     *
     * @param <T> il tipo dichiarato del campo (per esempio Item)
     */
    private class ClassNameAdapter<T> extends TypeAdapter<T> {

        private final Gson gson;
        private final Class<? super T> declaredType;

        ClassNameAdapter(Gson gson, Class<? super T> declaredType) {
            this.gson = gson;
            this.declaredType = declaredType;
        }

        @Override
        @SuppressWarnings("unchecked")
        public void write(JsonWriter out, T value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            Class<T> realClass = (Class<T>) value.getClass();
            TypeAdapter<T> delegate = gson.getDelegateAdapter(ClassNameAdapterFactory.this, TypeToken.get(realClass));

            JsonObject wrapper = new JsonObject();
            wrapper.addProperty(CLASS_KEY, realClass.getName());
            wrapper.add(DATA_KEY, delegate.toJsonTree(value));
            gson.toJson(wrapper, out);
        }

        @Override
        @SuppressWarnings("unchecked")
        public T read(JsonReader in) throws IOException {
            JsonElement element = gson.fromJson(in, JsonElement.class);
            if (element == null || element.isJsonNull()) {
                return null;
            }
            JsonObject wrapper = element.getAsJsonObject();
            String className = wrapper.get(CLASS_KEY).getAsString();

            Class<T> realClass;
            try {
                realClass = (Class<T>) Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new JsonParseException("Unknown class in save file: " + className, e);
            }
            // per sicurezza accetta solo classi del gioco compatibili con il campo
            if (!className.startsWith(GAME_PACKAGE) || !declaredType.isAssignableFrom(realClass)) {
                throw new JsonParseException("Invalid class in save file: " + className);
            }

            TypeAdapter<T> delegate = gson.getDelegateAdapter(ClassNameAdapterFactory.this, TypeToken.get(realClass));
            return delegate.fromJsonTree(wrapper.get(DATA_KEY));
        }
    }
}