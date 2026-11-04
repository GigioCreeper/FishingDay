package it.unicam.cs.mpgc.rpg129382.model;

import java.util.Objects;

/**
 * A species of fish and its characteristics.
 *
 * @param name     the name of the species, also used to find its image.
 * @param value    the coins the fish is worth when sold.
 * @param speed    how fast the fish swims, in pixels per second.
 * @param strength how hard the fish pulls when hooked, in the same unit as the rod power.
 */
public record FishType(String name, int value, double speed, double strength) {

    /**
     * @throws IllegalArgumentException if the name is blank, the value is negative,
     *                                  or the speed or the strength are not positive.
     */
    public FishType {
        Objects.requireNonNull(name);
        if (name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        if (value < 0) {
            throw new IllegalArgumentException("value must not be negative");
        }
        if (speed <= 0) {
            throw new IllegalArgumentException("speed must be positive");
        }
        if (strength <= 0) {
            throw new IllegalArgumentException("strength must be positive");
        }
    }
}