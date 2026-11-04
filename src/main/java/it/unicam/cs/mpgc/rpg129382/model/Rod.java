package it.unicam.cs.mpgc.rpg129382.model;

import java.util.Objects;

/**
 * A fishing rod. Its power is measured in the same unit as the fish strength:
 * the higher it is, the easier it is to hold strong fishes during a fight.
 *
 * @param name  the name of the rod, also used to find its image.
 * @param price the coins needed to buy it.
 * @param power how well it holds a pulling fish.
 */
public record Rod(String name, int price, double power) {

    /**
     * @throws IllegalArgumentException if the name is blank, the price is negative or the power is not positive.
     */
    public Rod {
        Objects.requireNonNull(name);
        if (name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        if (price < 0) {
            throw new IllegalArgumentException("price must not be negative");
        }
        if (power <= 0) {
            throw new IllegalArgumentException("power must be positive");
        }
    }
}