package it.unicam.cs.mpgc.rpg129382.logic;

/**
 * Something that advances in time, driven by the game loop.
 */
public interface Updatable {

    /**
     * Advances by one tick.
     * @param delta the seconds elapsed since the last tick.
     */
    void update(double delta);
}
