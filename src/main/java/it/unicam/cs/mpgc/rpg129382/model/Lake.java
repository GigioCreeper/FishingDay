package it.unicam.cs.mpgc.rpg129382.model;

import java.util.Objects;

/**
 * The lake where the player fishes.
 *
 * @param water     the area covered by water: the hook can never leave it.
 * @param catchZone the area where a hooked fish must be brought to be caught.
 */
public record Lake(Area water, Area catchZone) {

    /**
     * Creates a lake, checking that both areas are present.
     *
     * @throws NullPointerException if the water or the catch zone is null.
     */
    public Lake {
        Objects.requireNonNull(water);
        Objects.requireNonNull(catchZone);
    }
}
