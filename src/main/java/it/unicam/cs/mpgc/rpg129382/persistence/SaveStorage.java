package it.unicam.cs.mpgc.rpg129382.persistence;

import java.util.Optional;

/**
 * Where the game is saved. There is a single save: saving again replaces it.
 */
public interface SaveStorage {

    /**
     * Saves the game, replacing the previous save.
     *
     * @param data the state of the game to save.
     * @throws java.io.UncheckedIOException if the save cannot be written.
     */
    void save(SaveData data);

    /**
     * {@return the saved game, or empty if there is none or it cannot be read as a valid save}
     */
    Optional<SaveData> load();
}
