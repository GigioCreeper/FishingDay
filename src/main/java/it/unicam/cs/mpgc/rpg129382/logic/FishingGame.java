package it.unicam.cs.mpgc.rpg129382.logic;

import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.model.Controllable;
import it.unicam.cs.mpgc.rpg129382.model.Fish;
import it.unicam.cs.mpgc.rpg129382.model.Inventory;
import it.unicam.cs.mpgc.rpg129382.model.Positioned;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class FishingGame {
    private final Controllable hook;
    private final FishPopulation population;
    private final CatchDetector catchDetector;
    private final Lake lake;
    private final Inventory inventory;
    private final Random random;
    private Fight fight;
    private double directionX;
    private double directionY;

    public FishingGame(Controllable hook, FishPopulation population, CatchDetector catchDetector,
                       Lake lake, Inventory inventory, Random random) {
        this.hook = Objects.requireNonNull(hook);
        this.population = Objects.requireNonNull(population);
        this.catchDetector = Objects.requireNonNull(catchDetector);
        this.lake = Objects.requireNonNull(lake);
        this.inventory = Objects.requireNonNull(inventory);
        this.random = Objects.requireNonNull(random);
    }

    public Positioned getHook() {
        return hook;
    }

    public List<Fish> getFishes() {
        return population.getFishes();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Area getCatchZone() {
        return lake.catchZone();
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
        population.update(delta);
        updateFight(delta);
        collectFishInCatchZone();
    }

    private void moveHook(double delta) {
        if (fight == null) {
            moveByPlayer(GameConfig.HOOK_SPEED, delta);
        } else if (fight.isFishPulling()) {
            pullHook(delta);
        } else {
            fight.turnTowards(directionX);
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
        lake.water().keepInside(hook);
    }

    private void updateFight(double delta) {
        if (fight == null) {
            catchDetector.findCatch(hook, population.getFishes()).ifPresent(this::startFight);
            return;
        }
        fight.update(delta);
        attachToHook(fight.getFish());
        if (hasHookLeftWater()) {
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

    private boolean hasHookLeftWater() {
        return !lake.water().contains(hook.getX(), hook.getY());
    }

    private void breakLine() {
        population.remove(fight.getFish());
        fight = null;
        keepHookInWater();
    }

    private void collectFishInCatchZone() {
        if (fight == null) {
            return;
        }
        Fish fish = fight.getFish();
        if (lake.catchZone().contains(fish.getX(), fish.getY())) {
            inventory.add(fish.getType());
            population.remove(fish);
            fight = null;
        }
    }
}