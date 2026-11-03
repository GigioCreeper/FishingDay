package it.unicam.cs.mpgc.rpg129382.logic.fishing;

/**
 * Rules for spawning fish around the water.
 * @param minDelay the minimum seconds between two spawns.
 * @param maxDelay the maximum seconds between two spawns.
 */
public record SpawnRules(double minDelay, double maxDelay) {

    public SpawnRules {
        if (minDelay <= 0 || maxDelay < minDelay) {
            throw new IllegalArgumentException("invalid spawn delays");
        }
    }
}
