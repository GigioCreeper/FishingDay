package it.unicam.cs.mpgc.rpg129382.logic.session;

import it.unicam.cs.mpgc.rpg129382.config.FishCatalog;
import it.unicam.cs.mpgc.rpg129382.config.RodCatalog;
import it.unicam.cs.mpgc.rpg129382.logic.story.StoryProgress;
import it.unicam.cs.mpgc.rpg129382.model.FishType;
import it.unicam.cs.mpgc.rpg129382.model.Player;
import it.unicam.cs.mpgc.rpg129382.persistence.SaveData;
import it.unicam.cs.mpgc.rpg129382.persistence.SaveStorage;

import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * The game being played: starts a new one, continues the saved one and saves the progress.
 * It changes the player and the story in place, so every screen that shares them sees the new state.
 */
public final class GameSession {
    private final Player player;
    private final StoryProgress story;
    private final SaveStorage storage;
    private boolean started;

    /**
     * Creates a session with no game started yet.
     *
     * @param player  the player to reset or restore.
     * @param story   the story to reset or restore.
     * @param storage where the game is saved.
     */
    public GameSession(Player player, StoryProgress story, SaveStorage storage) {
        this.player = Objects.requireNonNull(player);
        this.story = Objects.requireNonNull(story);
        this.storage = Objects.requireNonNull(storage);
    }

    /**
     * Saves the current state of the game. Before a game is started or continued it does nothing,
     * so that the save on disk is not overwritten with an empty one.
     * If the save fails, the error is printed and the game goes on.
     */
    public void save() {
        if (started) {
            try {
                storage.save(snapshot());
            } catch (UncheckedIOException e) {
                System.err.println("Failed to save the game: " + e.getCause().getMessage());
            }
        }
    }

    /**
     * Starts a new game from the beginning: the starting rod, no coins, no fishes, no chapters read.
     * The save on disk is replaced the next time the game is saved.
     */
    public void newGame() {
        player.restore(RodCatalog.starter(), 0, Map.of());
        story.restore(0, false);
        started = true;
    }

    /**
     * {@return true if there is a saved game that can be continued: it must be valid
     * and its story must not be concluded yet}
     */
    public boolean canContinue() {
        return continuableSave().isPresent();
    }

    /**
     * Restores the saved game.
     *
     * @throws IllegalStateException if there is no game that can be continued.
     */
    public void continueGame() {
        SaveData save = continuableSave()
                .orElseThrow(() -> new IllegalStateException("No game to continue"));
        Map<FishType, Integer> fishes = new HashMap<>();
        save.fishes().forEach((name, count) -> fishes.put(FishCatalog.findByName(name).orElseThrow(), count));
        player.restore(RodCatalog.findByName(save.rodName()).orElseThrow(), save.money(), fishes);
        story.restore(save.chaptersRead(), save.storyCompleted());
        started = true;
    }

    private Optional<SaveData> continuableSave() {
        return storage.load().filter(save -> !save.storyCompleted() && isValid(save));
    }

    private boolean isValid(SaveData save) {
        return RodCatalog.findByName(save.rodName()).isPresent()
                && save.chaptersRead() <= story.chapterCount()
                && save.fishes().keySet().stream()
                .allMatch(name -> FishCatalog.findByName(name).isPresent());
    }

    private SaveData snapshot() {
        Map<String, Integer> fishes = new HashMap<>();
        player.getInventory().getFishes()
                .forEach((type, count) -> fishes.put(type.name(), count));
        return new SaveData(player.getMoney(), player.getRod().name(), fishes,
                story.getChaptersRead(), story.isConcluded());
    }
}
