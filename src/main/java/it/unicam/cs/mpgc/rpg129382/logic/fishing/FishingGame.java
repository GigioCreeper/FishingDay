package it.unicam.cs.mpgc.rpg129382.logic.fishing;

import it.unicam.cs.mpgc.rpg129382.logic.Updatable;
import it.unicam.cs.mpgc.rpg129382.logic.fishing.fight.Fight;
import it.unicam.cs.mpgc.rpg129382.logic.fishing.fight.FightFactory;
import it.unicam.cs.mpgc.rpg129382.logic.story.StoryMessage;
import it.unicam.cs.mpgc.rpg129382.model.Area;
import it.unicam.cs.mpgc.rpg129382.model.Fish;
import it.unicam.cs.mpgc.rpg129382.model.Lake;
import it.unicam.cs.mpgc.rpg129382.model.Player;
import it.unicam.cs.mpgc.rpg129382.model.Positioned;
import it.unicam.cs.mpgc.rpg129382.model.ReadOnlyFish;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A fishing session: the player moves the hook, hooks fishes and fights them, and picks up the
 * bottles that carry the story. It coordinates the objects of the lake and decides the outcome of
 * each catch; while a story message is open, the session is paused.
 */
public class FishingGame implements FishingState, FishingCommands, Updatable {
    private final HookControl hookControl;
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

    /**
     * Creates a game with no fish on the hook and no story message open.
     *
     * @param hookControl       moves the hook the player controls.
     * @param population        the common fishes of the lake.
     * @param bottles           the bottles that carry the story.
     * @param finalFish         the final fish of the story.
     * @param catchDetector     decides when the hook touches a fish or a bottle.
     * @param lake              the water and the catch zone.
     * @param player            the player, who gets the caught fishes and pays the penalties.
     * @param fightFactory      creates the fight when a fish gets hooked.
     * @param lineBrokenPenalty the coins the player loses when the line breaks.
     * @throws IllegalArgumentException if the penalty is negative.
     */
    public FishingGame(HookControl hookControl, FishPopulation population, StoryBottles bottles,
                       FinalFish finalFish, CatchDetector catchDetector, Lake lake, Player player,
                       FightFactory fightFactory, int lineBrokenPenalty) {
        if (lineBrokenPenalty < 0) {
            throw new IllegalArgumentException("lineBrokenPenalty must not be negative");
        }
        this.hookControl = Objects.requireNonNull(hookControl);
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
        return hookControl.getHook();
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
            hookControl.moveFree(directionX, directionY, delta);
            return;
        }
        hookControl.move(fight.step(directionX, directionY, delta));
        if (fight.keepsHookInWater()) {
            hookControl.keepInWater();
        }
    }

    private void updateFight(double delta) {
        if (fight == null) {
            catchDetector.findCatch(hookControl.getHook(), population.getFishes()).ifPresent(this::startFight);
        } else {
            fight.update(delta);
            hookControl.attach(fight.getFish());
        }
        if (fight != null) {
            resolveFight();
        }
    }

    private void pickUpBottle() {
        if (fight != null || storyMessage != null) {
            return;
        }
        bottles.getBottle()
                .filter(bottle -> catchDetector.touches(hookControl.getHook(), bottle))
                .ifPresent(_ -> storyMessage = bottles.pickUp());
    }

    private void startFight(Fish fish) {
        fish.hook();
        fight = fightFactory.create(fish);
        hookControl.attach(fish);
    }

    private void resolveFight() {
        Fish fish = fight.getFish();
        switch (fight.outcome(hookControl.getHook(), lake)) {
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
                hookControl.keepInWater();
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