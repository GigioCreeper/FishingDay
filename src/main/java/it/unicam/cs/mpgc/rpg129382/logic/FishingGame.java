package it.unicam.cs.mpgc.rpg129382.logic;

import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.model.Controllable;
import it.unicam.cs.mpgc.rpg129382.model.Fish;
import it.unicam.cs.mpgc.rpg129382.model.Inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class FishingGame {
    private final Controllable hook;
    private final FishSpawner spawner;
    private final CatchDetector catchDetector;
    private final CatchZone catchZone;
    private final Inventory inventory;
    private final Random random;
    private final List<Fish> fishes = new ArrayList<>();
    private Fight fight;
    private double directionX;
    private double directionY;
    private double spawnCooldown;

    public FishingGame(Controllable hook, FishSpawner spawner, CatchDetector catchDetector,
                       CatchZone catchZone, Inventory inventory, Random random) {
        this.hook = Objects.requireNonNull(hook);
        this.spawner = Objects.requireNonNull(spawner);
        this.catchDetector = Objects.requireNonNull(catchDetector);
        this.catchZone = Objects.requireNonNull(catchZone);
        this.inventory = Objects.requireNonNull(inventory);
        this.random = Objects.requireNonNull(random);
        this.spawnCooldown = spawner.nextSpawnDelay();
    }

    public Controllable getHook() {
        return hook;
    }

    public List<Fish> getFishes() {
        return Collections.unmodifiableList(fishes);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public CatchZone getCatchZone() {
        return catchZone;
    }

    public boolean hasHookedFish() {
        return fight != null;
    }

    public boolean isFishPulling() {
        return fight != null && fight.isFishPulling();
    }

    public void setDirection(double directionX, double directionY) {
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public void update(double delta) {
        moveHook(delta);
        updateFishes(delta);
        updateFight(delta);
        collectFishInCatchZone();
    }

    private void moveHook(double delta) {
        if (fight == null) {
            moveByPlayer(GameConfig.HOOK_SPEED, delta);
        } else if (fight.isFishPulling()) {
            pullHook(delta);
        } else {
            moveByPlayer(GameConfig.HOOKED_SPEED, delta);
        }
    }

    private void moveByPlayer(double speed, double delta) {
        double distance = speed * delta;
        hook.move(directionX * distance, directionY * distance);
        keepHookInWater();
    }

    private void pullHook(double delta) {
        double distance = fight.pullSpeed() * delta;
        hook.move(fight.pullDirectionX() * distance, fight.pullDirectionY() * distance);
    }

    private void keepHookInWater() {
        double x = Math.clamp(hook.getX(), 0, GameConfig.WINDOW_WIDTH);
        double y = Math.clamp(hook.getY(), GameConfig.WATER_TOP, GameConfig.WINDOW_HEIGHT);
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

    private void updateFight(double delta) {
        if (fight == null) {
            catchDetector.findCatch(hook, fishes).ifPresent(this::startFight);
            return;
        }
        fight.update(delta);
        attachToHook(fight.getFish());
        if (hasHookLeftScreen()) {
            breakLine();
        }
    }

    private void startFight(Fish fish) {
        fish.hook();
        fight = new Fight(fish, random);
        attachToHook(fish);
    }

    private void attachToHook(Fish fish) {
        fish.move(hook.getX() - fish.getX(), hook.getY() - fish.getY());
    }

    private boolean hasHookLeftScreen() {
        return hook.getX() < 0
                || hook.getX() > GameConfig.WINDOW_WIDTH
                || hook.getY() > GameConfig.WINDOW_HEIGHT;
    }

    private void breakLine() {
        fishes.remove(fight.getFish());
        fight = null;
        keepHookInWater();
    }

    private void collectFishInCatchZone() {
        if (fight == null) {
            return;
        }
        Fish fish = fight.getFish();
        if (catchZone.contains(fish.getX(), fish.getY())) {
            inventory.add(fish.getType());
            fishes.remove(fish);
            fight = null;
        }
    }
}