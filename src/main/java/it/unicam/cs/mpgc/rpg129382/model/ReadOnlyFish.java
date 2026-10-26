package it.unicam.cs.mpgc.rpg129382.model;

/**
 * Read-only view of a fish, used by whoever only needs to observe it.
 */
public interface ReadOnlyFish extends Positioned {

    FishType getType();

    Heading getHeading();

    boolean isHooked();
}
