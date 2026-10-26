package it.unicam.cs.mpgc.rpg129382.logic.fight;

import it.unicam.cs.mpgc.rpg129382.model.Fish;

/**
 * A phase of a fight between the player and a hooked fish.
 * Each phase defines its own duration and how the hook moves while it lasts.
 */
public interface FightPhase {

    /**
     * @return true if the player's input moves the hook during this phase.
     */
    boolean playerControls();

    /**
     * @return true if the hook must be kept inside the water during this phase.
     */
    boolean keepsHookInWater();

    /**
     * @return how long this phase lasts, in seconds.
     */
    double duration();

    /**
     * Called once when the fight enters this phase.
     * @param fish the hooked fish.
     */
    void enter(Fish fish);

    /**
     * Advances the phase by one tick.
     * @param fish the hooked fish, which the phase may turn.
     * @param directionX the horizontal direction pressed by the player.
     * @param directionY the vertical direction pressed by the player.
     * @param delta the seconds elapsed since the last tick.
     * @return how far the hook moves in this tick.
     */
    Displacement step(Fish fish, double directionX, double directionY, double delta);
}