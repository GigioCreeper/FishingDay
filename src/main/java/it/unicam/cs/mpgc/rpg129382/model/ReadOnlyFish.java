package it.unicam.cs.mpgc.rpg129382.model;

/**
 * Read-only view of a fish, used by whoever only needs to observe it.
 */
public interface ReadOnlyFish extends Positioned {

    /**
     * {@return the type of the fish}
     */
    FishType getType();

    /**
     * {@return the direction the fish is facing}
     */
    Heading getHeading();

    /**
     * {@return true if the fish is caught on the hook}
     */
    boolean isHooked();
}
