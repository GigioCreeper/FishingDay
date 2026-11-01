package it.unicam.cs.mpgc.rpg129382.logic.fishing;

import it.unicam.cs.mpgc.rpg129382.config.FishCatalog;
import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.logic.fishing.fight.ControlPhase;
import it.unicam.cs.mpgc.rpg129382.logic.fishing.fight.DurationRange;
import it.unicam.cs.mpgc.rpg129382.logic.fishing.fight.Fight;
import it.unicam.cs.mpgc.rpg129382.logic.fishing.fight.FightFactory;
import it.unicam.cs.mpgc.rpg129382.logic.fishing.fight.PullPhase;
import it.unicam.cs.mpgc.rpg129382.logic.story.StoryProgress;
import it.unicam.cs.mpgc.rpg129382.model.Area;
import it.unicam.cs.mpgc.rpg129382.model.Hook;
import it.unicam.cs.mpgc.rpg129382.model.Lake;
import it.unicam.cs.mpgc.rpg129382.model.Player;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Builds a fishing session from the values in {@link GameConfig} and {@link FishCatalog},
 * the rod the player is using and the story the bottles carry.
 */
public final class FishingGameFactory {

    private FishingGameFactory() {
    }

    public static FishingGame create(Player player, StoryProgress story, Random random) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(story);
        Objects.requireNonNull(random);
        Lake lake = createLake();
        Hook hook = new Hook(GameConfig.HOOK_START_X, GameConfig.HOOK_START_Y);
        SpawnRules spawnRules = new SpawnRules(
                GameConfig.FISH_SPAWN_MARGIN,
                GameConfig.SPAWN_MIN_SECONDS,
                GameConfig.SPAWN_MAX_SECONDS);
        Area spawnArea = new Area(0, GameConfig.WINDOW_WIDTH, GameConfig.FISH_SPAWN_TOP, GameConfig.WINDOW_HEIGHT);
        FishSpawner spawner = new FishSpawner(FishCatalog.all(), spawnArea, spawnRules, random);
        FishPopulation population = new FishPopulation(spawner, GameConfig.MAX_FISH);
        StoryBottles bottles = createStoryBottles(story, lake.water(), random);
        FinalFish finalFish = new FinalFish(story, FishCatalog.LEGEND, spawner, population,
                new DurationRange(GameConfig.FINAL_FISH_MIN_SECONDS, GameConfig.FINAL_FISH_MAX_SECONDS),
                random);
        CatchDetector catchDetector = new CatchDetector(GameConfig.CATCH_RADIUS);
        return new FishingGame(hook, GameConfig.HOOK_SPEED, population, bottles, finalFish, catchDetector,
                lake, player, createFightFactory(random, player.getRod().power()),
                GameConfig.LINE_BROKEN_PENALTY);
    }

    private static Lake createLake() {
        Area water = new Area(0, GameConfig.WINDOW_WIDTH, GameConfig.WATER_TOP, GameConfig.WINDOW_HEIGHT);
        Area catchZone = new Area(
                GameConfig.CATCH_ZONE_LEFT,
                GameConfig.CATCH_ZONE_RIGHT,
                GameConfig.CATCH_ZONE_TOP,
                GameConfig.CATCH_ZONE_BOTTOM);
        return new Lake(water, catchZone);
    }

    private static StoryBottles createStoryBottles(StoryProgress story, Area water, Random random) {
        return new StoryBottles(story, water,
                GameConfig.BOTTLE_SURFACE_Y,
                GameConfig.FISH_SPAWN_MARGIN,
                GameConfig.BOTTLE_SPEED,
                new DurationRange(GameConfig.BOTTLE_MIN_SECONDS, GameConfig.BOTTLE_MAX_SECONDS),
                random);
    }

    private static FightFactory createFightFactory(Random random, double rodPower) {
        PullPhase pull = new PullPhase(random,
                new DurationRange(GameConfig.PULL_MIN_SECONDS, GameConfig.PULL_MAX_SECONDS),
                rodPower);
        ControlPhase control = new ControlPhase(random,
                new DurationRange(GameConfig.CONTROL_MIN_SECONDS, GameConfig.CONTROL_MAX_SECONDS),
                GameConfig.HOOKED_SPEED);
        return fish -> new Fight(fish, List.of(pull, control));
    }
}