package it.unicam.cs.mpgc.rpg129382.logic.fishing;

/**
 * Commands that the player's input can send to a fishing session.
 */
public interface FishingCommands {

    void setDirection(double directionX, double directionY);

    /**
     * Closes the story message and resumes fishing.
     */
    void closeStoryMessage();
}