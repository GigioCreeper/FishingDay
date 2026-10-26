package it.unicam.cs.mpgc.rpg129382.logic.fishing;

import it.unicam.cs.mpgc.rpg129382.model.Area;
import it.unicam.cs.mpgc.rpg129382.model.Fish;
import it.unicam.cs.mpgc.rpg129382.model.FishType;
import it.unicam.cs.mpgc.rpg129382.model.Heading;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class FishSpawner {
    private final List<FishType> types;
    private final Area water;
    private final SpawnRules rules;
    private final Random random;

    public FishSpawner(List<FishType> types, Area water, SpawnRules rules, Random random) {
        if (types.isEmpty()) {
            throw new IllegalArgumentException("at least one fish type is required");
        }
        this.types = List.copyOf(types);
        this.water = Objects.requireNonNull(water);
        this.rules = Objects.requireNonNull(rules);
        this.random = Objects.requireNonNull(random);
    }

    public Fish spawn() {
        FishType type = types.get(random.nextInt(types.size()));
        Heading heading = random.nextBoolean() ? Heading.RIGHT : Heading.LEFT;
        double x = heading == Heading.RIGHT
                ? water.left() - rules.margin()
                : water.right() + rules.margin();
        double y = random.nextDouble(water.top(), water.bottom());
        return new Fish(type, heading, x, y);
    }

    public double nextSpawnDelay() {
        return random.nextDouble(rules.minDelay(), rules.maxDelay());
    }

    public boolean hasLeft(Fish fish) {
        return fish.getX() < water.left() - rules.margin()
                || fish.getX() > water.right() + rules.margin();
    }
}
