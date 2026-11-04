package it.unicam.cs.mpgc.rpg129382.model;

import java.util.Objects;

/**
 * The lake where the player fishes.
 *
 * @param water     the area covered by water: the hook can never leave it.
 * @param catchZone the area where a hooked fish must be brought to be caught.
 */
public record Lake(Area water, Area catchZone) {

    public Lake {
        Objects.requireNonNull(water);
        Objects.requireNonNull(catchZone);
    }
}
