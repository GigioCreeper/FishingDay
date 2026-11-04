package it.unicam.cs.mpgc.rpg129382.logic.fishing.fight;

/**
 * How a fight is going.
 */
public enum FightOutcome {
    /** The fight goes on. */
    ONGOING,
    /** The fish pulled the hook out of the water: the fish is lost. */
    LINE_BROKEN,
    /** The fish reached the catch zone: the player caught it. */
    CAUGHT
}
