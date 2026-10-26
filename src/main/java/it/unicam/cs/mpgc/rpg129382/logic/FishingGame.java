package it.unicam.cs.mpgc.rpg129382.logic;

import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.logic.fight.Displacement;
import it.unicam.cs.mpgc.rpg129382.logic.fight.Fight;
import it.unicam.cs.mpgc.rpg129382.logic.fight.FightFactory;
import it.unicam.cs.mpgc.rpg129382.model.Controllable;
import it.unicam.cs.mpgc.rpg129382.model.Fish;
import it.unicam.cs.mpgc.rpg129382.model.Inventory;
import it.unicam.cs.mpgc.rpg129382.model.Positioned;

import java.util.List;
import java.util.Objects;

public class FishingGame implements FishingState, FishingCommands {
    private final Controllable hook;
    private final FishPopulation population;
    private final CatchDetector catchDetector;
    private final Lake lake;
    private final Inventory inventory;
    private final FightFactory fightFactory;
    private Fight fight;
    private double directionX;
    private double directionY;

    public FishingGame(Controllable hook, FishPopulation population, CatchDetector catchDetector,
                       Lake lake, Inventory inventory, FightFactory fightFactory) {
        this.hook = Objects.requireNonNull(hook);
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
    public List<Fish> getFishes() {
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
        return fight != null && !fight.playerControls();
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
        collectFishInCatchZone();
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
        double distance = GameConfig.HOOK_SPEED * delta;
        hook.move(directionX * distance, directionY * distance);
        keepHookInWater();
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
        fight = fightFactory.create(fish);
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