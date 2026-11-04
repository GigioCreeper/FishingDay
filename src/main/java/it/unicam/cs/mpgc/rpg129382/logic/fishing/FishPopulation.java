package it.unicam.cs.mpgc.rpg129382.logic.fishing;

import it.unicam.cs.mpgc.rpg129382.model.Fish;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * The fishes swimming in the lake. Common fishes come from the spawner: they swim, leave the lake
 * and are replaced by new ones, up to a maximum. Other fishes, like the final one, can be added on top.
 */
public class FishPopulation {
    private final FishSpawner spawner;
    private final int maxFish;
    private final List<Fish> fishes = new ArrayList<>();
    private double spawnCooldown;

    /**
     * @param spawner where new fishes come from.
     * @param maxFish how many fishes the spawner can put in the lake at the same time.
     * @throws IllegalArgumentException if maxFish is not positive.
     */
    public FishPopulation(FishSpawner spawner, int maxFish) {
        if (maxFish <= 0) {
            throw new IllegalArgumentException("maxFish must be positive");
        }
        this.spawner = Objects.requireNonNull(spawner);
        this.maxFish = maxFish;
        this.spawnCooldown = spawner.nextSpawnDelay();
    }

    /**
     * @return the fishes in the lake; the list cannot be modified.
     */
    public List<Fish> getFishes() {
        return Collections.unmodifiableList(fishes);
    }

    /**
     * Adds a fish that does not come from the spawner, ignoring the population limit.
     *
     * @param fish the fish to add.
     */
    public void add(Fish fish) {
        fishes.add(Objects.requireNonNull(fish));
    }

    /**
     * Takes a fish out of the lake, because it was caught or broke the line.
     *
     * @param fish the fish to remove.
     */
    public void remove(Fish fish) {
        fishes.remove(fish);
    }

    /**
     * Lets every fish swim, removes those that have left the lake
     * and adds a new one when it is time and there is room.
     *
     * @param delta the seconds elapsed since the last tick.
     */
    public void update(double delta) {
        fishes.forEach(fish -> fish.swim(delta));
        fishes.removeIf(spawner::hasLeft);

        spawnCooldown -= delta;
        if (spawnCooldown <= 0 && fishes.size() < maxFish) {
            fishes.add(spawner.spawn());
            spawnCooldown = spawner.nextSpawnDelay();
        }
    }
}
