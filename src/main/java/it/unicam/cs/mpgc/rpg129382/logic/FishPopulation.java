package it.unicam.cs.mpgc.rpg129382.logic;

import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.model.Fish;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FishPopulation {
    private final FishSpawner spawner;
    private final List<Fish> fishes = new ArrayList<>();
    private double spawnCooldown;

    public FishPopulation(FishSpawner spawner) {
        this.spawner = Objects.requireNonNull(spawner);
        this.spawnCooldown = spawner.nextSpawnDelay();
    }

    public List<Fish> getFishes() {
        return Collections.unmodifiableList(fishes);
    }

    public void remove(Fish fish) {
        fishes.remove(fish);
    }

    public void update(double delta) {
        fishes.forEach(fish -> fish.swim(delta));
        fishes.removeIf(this::hasLeftLake);

        spawnCooldown -= delta;
        if (spawnCooldown <= 0 && fishes.size() < GameConfig.MAX_FISH) {
            fishes.add(spawner.spawn());
            spawnCooldown = spawner.nextSpawnDelay();
        }
    }

    private boolean hasLeftLake(Fish fish) {
        return fish.getX() < -GameConfig.FISH_SPAWN_MARGIN
                || fish.getX() > GameConfig.WINDOW_WIDTH + GameConfig.FISH_SPAWN_MARGIN;
    }
}