package it.unicam.cs.mpgc.rpg129382.persistence;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;

/**
 * Saves the game in a JSON file.
 * The file is first written next to the old one and then moved over it in a single step,
 * so that a crash while saving never leaves a half-written save.
 * A file that is not valid JSON, or that holds invalid data, is treated as no save at all.
 */
public final class JsonSaveStorage implements SaveStorage {
    private final Gson gson = new Gson();
    private final Path filePath;

    /**
     * Creates a storage that uses the given file.
     *
     * @param filePath the file where the game is saved; its folder is created when needed.
     */
    public JsonSaveStorage(Path filePath) {
        this.filePath = Objects.requireNonNull(filePath);
    }

    @Override
    public void save(SaveData data) {
        Objects.requireNonNull(data);
        try {
            Files.createDirectories(filePath.getParent());
            Path temp = filePath.resolveSibling(filePath.getFileName() + ".tmp");
            Files.writeString(temp, gson.toJson(data));
            Files.move(temp, filePath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not save the game", e);
        }
    }

    @Override
    public Optional<SaveData> load() {
        if (Files.notExists(filePath)) {
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(gson.fromJson(Files.readString(filePath), SaveData.class));
        } catch (IOException e) {
            throw new UncheckedIOException("Could not load the game", e);
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }
}
