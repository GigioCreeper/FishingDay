package it.unicam.cs.mpgc.rpg129382.logic;

import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.model.Fish;
import it.unicam.cs.mpgc.rpg129382.model.FishType;
import it.unicam.cs.mpgc.rpg129382.model.Heading;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class FishSpawner {
    private final List<FishType> types;
    private final Random random;

    public FishSpawner(List<FishType> types, Random random) {
        if (types.isEmpty()) {
            throw new IllegalArgumentException("at least one fish type is required");
        }
        this.types = List.copyOf(types);
        this.random = Objects.requireNonNull(random);
    }

    public Fish spawn() {
        FishType type = types.get(random.nextInt(types.size()));
        Heading heading = random.nextBoolean() ? Heading.RIGHT : Heading.LEFT;
        double x = heading == Heading.RIGHT
                ? -GameConfig.FISH_SPAWN_MARGIN
                : GameConfig.WINDOW_WIDTH + GameConfig.FISH_SPAWN_MARGIN;
        double depth = GameConfig.WINDOW_HEIGHT - GameConfig.WATER_TOP;
        double y = GameConfig.WATER_TOP + random.nextDouble() * depth;
        return new Fish(type, heading, x, y);
    }

    public double nextSpawnDelay() {
        double range = GameConfig.SPAWN_MAX_SECONDS - GameConfig.SPAWN_MIN_SECONDS;
        return GameConfig.SPAWN_MIN_SECONDS + random.nextDouble() * range;
    }
}