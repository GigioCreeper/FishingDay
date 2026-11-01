package it.unicam.cs.mpgc.rpg129382.logic.fishing;

import it.unicam.cs.mpgc.rpg129382.logic.Updatable;
import it.unicam.cs.mpgc.rpg129382.logic.fishing.fight.Fight;
import it.unicam.cs.mpgc.rpg129382.logic.fishing.fight.FightFactory;
import it.unicam.cs.mpgc.rpg129382.logic.story.StoryMessage;
import it.unicam.cs.mpgc.rpg129382.model.Area;
import it.unicam.cs.mpgc.rpg129382.model.Displacement;
import it.unicam.cs.mpgc.rpg129382.model.Fish;
import it.unicam.cs.mpgc.rpg129382.model.Lake;
import it.unicam.cs.mpgc.rpg129382.model.Movable;
import it.unicam.cs.mpgc.rpg129382.model.Player;
import it.unicam.cs.mpgc.rpg129382.model.Positioned;
import it.unicam.cs.mpgc.rpg129382.model.ReadOnlyFish;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FishingGame implements FishingState, FishingCommands, Updatable {
    private final Movable hook;
    private final double hookSpeed;
    private final FishPopulation population;
    private final StoryBottles bottles;
    private final FinalFish finalFish;
    private final CatchDetector catchDetector;
    private final Lake lake;
    private final Player player;
    private final FightFactory fightFactory;
    private final int lineBrokenPenalty;
    private Fight fight;
    private StoryMessage storyMessage;
    private double directionX;
    private double directionY;

    public FishingGame(Movable hook, double hookSpeed, FishPopulation population, StoryBottles bottles,
                       FinalFish finalFish, CatchDetector catchDetector, Lake lake, Player player, FightFactory fightFactory,
                       int lineBrokenPenalty) {
        if (hookSpeed <= 0) {
            throw new IllegalArgumentException("hookSpeed must be positive");
        }
        if (lineBrokenPenalty < 0) {
            throw new IllegalArgumentException("lineBrokenPenalty must not be negative");
        }
        this.hook = Objects.requireNonNull(hook);
        this.hookSpeed = hookSpeed;
        this.population = Objects.requireNonNull(population);
        this.bottles = Objects.requireNonNull(bottles);
        this.finalFish = Objects.requireNonNull(finalFish);
        this.catchDetector = Objects.requireNonNull(catchDetector);
        this.lake = Objects.requireNonNull(lake);
        this.player = Objects.requireNonNull(player);
        this.fightFactory = Objects.requireNonNull(fightFactory);
        this.lineBrokenPenalty = lineBrokenPenalty;
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
        return player.getInventory().totalCount();
    }

    @Override
    public int getMoney() {
        return player.getMoney();
    }

    @Override
    public Optional<? extends Positioned> getBottle() {
        return bottles.getBottle();
    }

    @Override
    public Optional<StoryMessage> getStoryMessage() {
        return Optional.ofNullable(storyMessage);
    }

    @Override
    public void setDirection(double directionX, double directionY) {
        this.directionX = directionX;
        this.directionY = directionY;
    }

    @Override
    public void closeStoryMessage() {
        storyMessage = null;
    }

    @Override
    public void update(double delta) {
        if (storyMessage != null) {
            return;
        }
        moveHook(delta);
        population.update(delta);
        bottles.update(delta);
        finalFish.update(delta);
        updateFight(delta);
        pickUpBottle();
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

    private void pickUpBottle() {
        if (fight != null) {
            return;
        }
        bottles.getBottle()
                .filter(bottle -> catchDetector.touches(hook, bottle))
                .ifPresent(_ -> storyMessage = bottles.pickUp());
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
        Fish fish = fight.getFish();
        switch (fight.outcome(hook, lake)) {
            case CAUGHT -> {
                endFight();
                if (finalFish.is(fish)) {
                    storyMessage = finalFish.caught();
                } else {
                    player.getInventory().add(fish.getType());
                }
            }
            case LINE_BROKEN -> {
                endFight();
                payLineBrokenPenalty();
                keepHookInWater();
                if (finalFish.is(fish)) {
                    storyMessage = finalFish.escaped();
                }
            }
            case ONGOING -> {
            }
        }
    }

    private void payLineBrokenPenalty() {
        player.spend(Math.min(lineBrokenPenalty, player.getMoney()));
    }

    private void endFight() {
        population.remove(fight.getFish());
        fight = null;
    }
}