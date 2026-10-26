package it.unicam.cs.mpgc.rpg129382.logic;

import it.unicam.cs.mpgc.rpg129382.logic.fight.Fight;
import it.unicam.cs.mpgc.rpg129382.logic.fight.FightFactory;
import it.unicam.cs.mpgc.rpg129382.model.Area;
import it.unicam.cs.mpgc.rpg129382.model.Displacement;
import it.unicam.cs.mpgc.rpg129382.model.Fish;
import it.unicam.cs.mpgc.rpg129382.model.Inventory;
import it.unicam.cs.mpgc.rpg129382.model.Lake;
import it.unicam.cs.mpgc.rpg129382.model.Movable;
import it.unicam.cs.mpgc.rpg129382.model.Positioned;
import it.unicam.cs.mpgc.rpg129382.model.ReadOnlyFish;

import java.util.List;
import java.util.Objects;

public class FishingGame implements FishingState, FishingCommands, Updatable {
    private final Movable hook;
    private final double hookSpeed;
    private final FishPopulation population;
    private final CatchDetector catchDetector;
    private final Lake lake;
    private final Inventory inventory;
    private final FightFactory fightFactory;
    private Fight fight;
    private double directionX;
    private double directionY;

    public FishingGame(Movable hook, double hookSpeed, FishPopulation population, CatchDetector catchDetector,
                       Lake lake, Inventory inventory, FightFactory fightFactory) {
        if (hookSpeed <= 0) {
            throw new IllegalArgumentException("hookSpeed must be positive");
        }
        this.hook = Objects.requireNonNull(hook);
        this.hookSpeed = hookSpeed;
        this.population = Objects.requireNonNull(population);
        this.catchDetector = Objects.requireNonNull(catchDetector);
        this.lake = Objects.requireNonNull(lake);
        this.inventory = Objects.requireNonNull(inventory);
        this.fightFactory = Objects.requireNonNull(fightFactory);
    }

    @Override
    public Positioned getHook() {
        return hook;
    }

    @Override
    public List<? extends ReadOnlyFish> getFishes() {
        return population.getFishes();
    }

    @Override
    public Area getCatchZone() {
        return lake.catchZone();
    }

    @Override
    public boolean hasHookedFish() {
        return fight != null;
    }

    @Override
    public boolean isFishPulling() {
        return fight != null && fight.isFishPulling();
    }

    @Override
    public int getInventoryCount() {
        return inventory.totalCount();
    }

    @Override
    public void setDirection(double directionX, double directionY) {
        this.directionX = directionX;
        this.directionY = directionY;
    }

    @Override
    public void update(double delta) {
        moveHook(delta);
        population.update(delta);
        updateFight(delta);
    }

    private void moveHook(double delta) {
        if (fight == null) {
            moveFreeHook(delta);
            return;
        }
        Displacement displacement = fight.step(directionX, directionY, delta);
        hook.move(displacement.dx(), displacement.dy());
        if (fight.keepsHookInWater()) {
            keepHookInWater();
        }
    }

    private void moveFreeHook(double delta) {
        double distance = hookSpeed * delta;
        hook.move(directionX * distance, directionY * distance);
        keepHookInWater();
    }

    private void keepHookInWater() {
        Area water = lake.water();
        hook.move(water.clampX(hook.getX()) - hook.getX(), water.clampY(hook.getY()) - hook.getY());
    }

    private void updateFight(double delta) {
        if (fight == null) {
            catchDetector.findCatch(hook, population.getFishes()).ifPresent(this::startFight);
        } else {
            fight.update(delta);
            attachToHook(fight.getFish());
        }
        if (fight != null) {
            resolveFight();
        }
    }

    private void startFight(Fish fish) {
        fish.hook();
        fight = fightFactory.create(fish);
        attachToHook(fish);
    }

    private void attachToHook(Fish fish) {
        fish.move(hook.getX() - fish.getX(), hook.getY() - fish.getY());
    }

    private void resolveFight() {
        switch (fight.outcome(hook, lake)) {
            case CAUGHT -> {
                inventory.add(fight.getFish().getType());
                endFight();
            }
            case LINE_BROKEN -> {
                endFight();
                keepHookInWater();
            }
            case ONGOING -> {
            }
        }
    }

    private void endFight() {
        population.remove(fight.getFish());
        fight = null;
    }
}
