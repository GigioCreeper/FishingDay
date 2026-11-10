package it.unicam.cs.mpgc.rpg129382.logic.fishing;

import it.unicam.cs.mpgc.rpg129382.logic.Updatable;
import it.unicam.cs.mpgc.rpg129382.logic.story.StoryMessage;
import it.unicam.cs.mpgc.rpg129382.logic.story.StoryProgress;
import it.unicam.cs.mpgc.rpg129382.model.DurationRange;
import it.unicam.cs.mpgc.rpg129382.model.FishType;
import it.unicam.cs.mpgc.rpg129382.model.ReadOnlyFish;

import java.util.Objects;
import java.util.Random;

/**
 * Lets the final fish of the story swim in the lake once every chapter is read.
 * There is at most one at a time; if it swims away without being hooked, it comes back later.
 */
public final class FinalFish implements Updatable {
    private final StoryProgress story;
    private final FishType type;
    private final FishSpawner spawner;
    private final FishPopulation population;
    private final DurationRange delays;
    private final Random random;
    private double cooldown;

    /**
     * Creates the final fish, which waits a random time before its first appearance.
     *
     * @param story      the story the final fish concludes.
     * @param type       the type of the final fish.
     * @param spawner    where the final fish enters the water.
     * @param population the fishes of the lake, which the final fish joins.
     * @param delays     how long to wait before the final fish appears.
     * @param random     the source of randomness.
     */
    public FinalFish(StoryProgress story, FishType type, FishSpawner spawner, FishPopulation population,
                     DurationRange delays, Random random) {
        this.story = Objects.requireNonNull(story);
        this.type = Objects.requireNonNull(type);
        this.spawner = Objects.requireNonNull(spawner);
        this.population = Objects.requireNonNull(population);
        this.delays = Objects.requireNonNull(delays);
        this.random = Objects.requireNonNull(random);
        this.cooldown = delays.next(random);
    }

    /**
     * {@return true if it is the final fish}
     *
     * @param fish a fish of the lake.
     */
    public boolean is(ReadOnlyFish fish) {
        return fish.getType().equals(type);
    }

    /**
     * Waits a random time and then lets the final fish enter the lake, if the story is waiting for it
     * and it is not already swimming.
     *
     * @param delta the seconds elapsed since the last tick.
     */
    @Override
    public void update(double delta) {
        if (story.awaitsFinal() && !isSwimming()) {
            cooldown -= delta;
            if (cooldown <= 0) {
                population.add(spawner.spawn(type));
                cooldown = delays.next(random);
            }
        }
    }

    /**
     * Concludes the story, because the player caught the final fish.
     *
     * @return the happy ending of the story.
     * @throws IllegalStateException if the story is not waiting for the final fish.
     */
    public StoryMessage caught() {
        return story.winFinal();
    }

    /**
     * Concludes the story, because the final fish broke the line.
     *
     * @return the bad ending of the story.
     * @throws IllegalStateException if the story is not waiting for the final fish.
     */
    public StoryMessage escaped() {
        return story.loseFinal();
    }

    private boolean isSwimming() {
        return population.getFishes().stream().anyMatch(this::is);
    }
}
