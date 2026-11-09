package it.unicam.cs.mpgc.rpg129382.persistence;

import java.util.Map;
import java.util.Objects;

/**
 * The state of a game as it is saved. Rods and fish types are stored by name,
 * so the save does not depend on how the game objects are made.
 *
 * @param money          the coins the player owns.
 * @param rodName        the name of the rod the player is using.
 * @param fishes         how many fish the player owns, by name of their type.
 * @param chaptersRead   how many chapters of the story the player has read.
 * @param storyCompleted true if the story has reached one of its endings.
 */
public record SaveData(int money, String rodName, Map<String, Integer> fishes, int chaptersRead,
                       boolean storyCompleted) {
    /**
     * @throws NullPointerException     if the rod name or the fishes are missing.
     * @throws IllegalArgumentException if the money or the chapters read are negative,
     *                                  or a fish count is not positive.
     */
    public SaveData {
        Objects.requireNonNull(rodName);
        fishes = Map.copyOf(fishes);
        if (money < 0) {
            throw new IllegalArgumentException("Money cannot be negative");
        }
        if (chaptersRead < 0) {
            throw new IllegalArgumentException("Chapters read cannot be negative");
        }
        if (fishes.values().stream().anyMatch(count -> count <= 0)) {
            throw new IllegalArgumentException("Fish counts must be positive");
        }
    }
}
