package it.unicam.cs.mpgc.rpg129382.logic;

import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.model.Controllable;
import it.unicam.cs.mpgc.rpg129382.model.Fish;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FishingGame {
    private final Controllable hook;
    private final FishSpawner spawner;
    private final List<Fish> fishes = new ArrayList<>();
    private double directionX;
    private double directionY;
    private double spawnCooldown;

    public FishingGame(Controllable hook, FishSpawner spawner) {
        this.hook = Objects.requireNonNull(hook);
        this.spawner = Objects.requireNonNull(spawner);
        this.spawnCooldown = spawner.nextSpawnDelay();
    }

    public Controllable getHook() {
        return hook;
    }

    public List<Fish> getFishes() {
        return Collections.unmodifiableList(fishes);
    }

    public void setDirection(double directionX, double directionY) {
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public void update(double delta) {
        double distance = GameConfig.HOOK_SPEED * delta;
        hook.move(directionX * distance, directionY * distance);
        keepHookInBounds();
        updateFishes(delta);
    }

    private void keepHookInBounds() {
        //TODO: implement this method to keep the hook within the LAKE bounds instead of the window bounds
        double x = Math.clamp(hook.getX(), 0, GameConfig.WINDOW_WIDTH);
        double y = Math.clamp(hook.getY(), 0, GameConfig.WINDOW_HEIGHT);
        hook.move(x - hook.getX(), y - hook.getY());
    }

    private void updateFishes(double delta) {
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