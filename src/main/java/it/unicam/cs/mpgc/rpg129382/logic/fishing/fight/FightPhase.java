package it.unicam.cs.mpgc.rpg129382.logic.fishing.fight;

import it.unicam.cs.mpgc.rpg129382.model.geometry.Displacement;
import it.unicam.cs.mpgc.rpg129382.model.Fish;

/**
 * A phase of a fight between the player and a hooked fish.
 * Each phase is a strategy that defines its own duration and how the hook moves while it lasts;
 * the {@link Fight} decides when to move on to the next one.
 */
public interface FightPhase {

    /**
     * {@return true if the fish is pulling the hook and the player has no control over it}
     */
    boolean isFishPulling();

    /**
     * {@return true if the hook must be kept inside the water during this phase;
     * if false, the hook can leave it and the line breaks}
     */
    boolean keepsHookInWater();

    /**
     * {@return a new duration for this phase, in seconds}
     */
    double nextDuration();

    /**
     * Called once when the fight enters this phase.
     *
     * @param fish the hooked fish.
     */
    default void enter(Fish fish) {
    }

    /**
     * Advances the phase by one tick.
     *
     * @param fish       the hooked fish, which the phase may turn.
     * @param directionX the horizontal direction pressed by the player.
     * @param directionY the vertical direction pressed by the player.
     * @param delta      the seconds elapsed since the last tick.
     * @return how far the hook moves in this tick.
     */
    Displacement step(Fish fish, double directionX, double directionY, double delta);
}
