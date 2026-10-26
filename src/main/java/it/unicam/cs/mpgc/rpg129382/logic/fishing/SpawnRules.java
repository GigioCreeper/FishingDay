package it.unicam.cs.mpgc.rpg129382.logic.fishing;

/**
 * Rules for spawning fish around the water.
 * @param margin how far outside the water fish spawn and despawn.
 * @param minDelay the minimum seconds between two spawns.
 * @param maxDelay the maximum seconds between two spawns.
 */
public record SpawnRules(double margin, double minDelay, double maxDelay) {

    public SpawnRules {
        if (margin < 0) {
            throw new IllegalArgumentException("margin must not be negative");
        }
        if (minDelay <= 0 || maxDelay < minDelay) {
            throw new IllegalArgumentException("invalid spawn delays");
        }
    }
}
