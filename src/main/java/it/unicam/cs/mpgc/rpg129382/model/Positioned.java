package it.unicam.cs.mpgc.rpg129382.model;

/**
 * Something with a position in the game world.
 * Coordinates are in pixels of the virtual window, with the y axis pointing down.
 */
public interface Positioned {

    /**
     * @return the horizontal position.
     */
    double getX();

    /**
     * @return the vertical position, growing downwards.
     */
    double getY();
}
