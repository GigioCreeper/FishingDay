package it.unicam.cs.mpgc.rpg129382.logic.session;

import it.unicam.cs.mpgc.rpg129382.config.FishCatalog;
import it.unicam.cs.mpgc.rpg129382.config.RodCatalog;
import it.unicam.cs.mpgc.rpg129382.logic.story.StoryProgress;
import it.unicam.cs.mpgc.rpg129382.model.FishType;
import it.unicam.cs.mpgc.rpg129382.model.Player;
import it.unicam.cs.mpgc.rpg129382.persistence.SaveData;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Converts between the saved data and the player and story of the game,
 * and checks that a save refers only to rods and fish that exist.
 */
final class SaveDataMapper {
    private final Player player;
    private final StoryProgress story;

    /**
     * Creates a mapper for the given player and story.
     *
     * @param player the player to save or restore.
     * @param story  the story progress to save or restore.
     */
    SaveDataMapper(Player player, StoryProgress story) {
        this.player = Objects.requireNonNull(player);
        this.story = Objects.requireNonNull(story);
    }

    /**
     * {@return the current state of the player and the story, ready to be saved}
     */
    SaveData snapshot() {
        Map<String, Integer> fishes = new HashMap<>();
        player.getInventory().getFishes().forEach((type, count) -> fishes.put(type.name(), count));
        return new SaveData(player.getMoney(), player.getRod().name(), fishes,
                story.getChaptersRead(), story.isConcluded());
    }

    /**
     * {@return true if the save can be restored: its rod and fish exist in the catalogs
     * and it has not read more chapters than the story has}
     *
     * @param save the save to check.
     */
    boolean isValid(SaveData save) {
        return RodCatalog.findByName(save.rodName()).isPresent()
                && save.chaptersRead() <= story.chapterCount()
                && save.fishes().keySet().stream()
                .allMatch(name -> FishCatalog.findByName(name).isPresent());
    }

    /**
     * Puts the player and the story back in the saved state.
     *
     * @param save a save that {@link #isValid(SaveData) is valid}.
     * @throws java.util.NoSuchElementException if the save refers to a rod or a fish that does not exist.
     */
    void restore(SaveData save) {
        Map<FishType, Integer> fishes = new HashMap<>();
        save.fishes().forEach((name, count) -> fishes.put(FishCatalog.findByName(name).orElseThrow(), count));
        player.restore(RodCatalog.findByName(save.rodName()).orElseThrow(), save.money(), fishes);
        story.restore(save.chaptersRead(), save.storyCompleted());
    }
}
