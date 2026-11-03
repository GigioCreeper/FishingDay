package it.unicam.cs.mpgc.rpg129382.logic.fishing;

import it.unicam.cs.mpgc.rpg129382.model.Fish;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FishPopulation {
    private final FishSpawner spawner;
    private final int maxFish;
    private final List<Fish> fishes = new ArrayList<>();
    private double spawnCooldown;

    public FishPopulation(FishSpawner spawner, int maxFish) {
        if (maxFish <= 0) {
            throw new IllegalArgumentException("maxFish must be positive");
        }
        this.spawner = Objects.requireNonNull(spawner);
        this.maxFish = maxFish;
        this.spawnCooldown = spawner.nextSpawnDelay();
    }

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

    public void remove(Fish fish) {
        fishes.remove(fish);
    }

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
