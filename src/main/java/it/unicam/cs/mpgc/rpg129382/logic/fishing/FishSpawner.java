package it.unicam.cs.mpgc.rpg129382.logic.fishing;

import it.unicam.cs.mpgc.rpg129382.logic.fishing.fight.DurationRange;
import it.unicam.cs.mpgc.rpg129382.model.*;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class FishSpawner {
    private final List<FishType> types;
    private final LakeEdges edges;
    private final DurationRange delays;
    private final Random random;

    public FishSpawner(List<FishType> types, LakeEdges edges, DurationRange delays, Random random) {
        if (types.isEmpty()) {
            throw new IllegalArgumentException("at least one fish type is required");
        }
        this.types = List.copyOf(types);
        this.edges = Objects.requireNonNull(edges);
        this.delays = Objects.requireNonNull(delays);
        this.random = Objects.requireNonNull(random);
    }

    public Fish spawn() {
        return spawn(types.get(random.nextInt(types.size())));
    }

    /**
     * @param type the type of the fish to spawn, even if it is not among the common ones.
     * @return a new fish entering the water from one of its sides.
     */
    public Fish spawn(FishType type) {
        Objects.requireNonNull(type);
        Heading heading = Heading.random(random);
        double x = edges.entryX(heading);
        double y = random.nextDouble(edges.area().top(), edges.area().bottom());
        return new Fish(type, heading, x, y);
    }

    public double nextSpawnDelay() {
        return delays.next(random);
    }

    public boolean hasLeft(Fish fish) {
        return edges.hasLeft(fish);
    }
}
