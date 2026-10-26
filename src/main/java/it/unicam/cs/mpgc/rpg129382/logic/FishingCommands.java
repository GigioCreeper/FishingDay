package it.unicam.cs.mpgc.rpg129382.logic;

/**
 * Commands that the controller can send to a fishing session.
 */
public interface FishingCommands {

    void setDirection(double directionX, double directionY);

    void update(double delta);
}