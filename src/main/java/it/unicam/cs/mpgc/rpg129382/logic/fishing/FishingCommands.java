package it.unicam.cs.mpgc.rpg129382.logic.fishing;

/**
 * Commands that the player's input can send to a fishing session.
 */
public interface FishingCommands {

    /**
     * Sets where the player is pushing the hook; it keeps moving that way until the direction changes.
     *
     * @param directionX -1 for left, 1 for right, 0 for neither.
     * @param directionY -1 for up, 1 for down, 0 for neither.
     */
    void setDirection(double directionX, double directionY);

    /**
     * Closes the story message and resumes fishing.
     */
    void closeStoryMessage();
}