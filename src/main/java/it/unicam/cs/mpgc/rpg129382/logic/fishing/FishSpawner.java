package it.unicam.cs.mpgc.rpg129382.logic.fishing;

import it.unicam.cs.mpgc.rpg129382.logic.DurationRange;
import it.unicam.cs.mpgc.rpg129382.model.Fish;
import it.unicam.cs.mpgc.rpg129382.model.FishType;
import it.unicam.cs.mpgc.rpg129382.model.Heading;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Creates the fishes that enter the lake from its sides, and decides when they have left it.
 */
public class FishSpawner {
    private final List<FishType> types;
    private final LakeEdges edges;
    private final DurationRange delays;
    private final Random random;

    /**
     * Creates a spawner of the given types of fish.
     *
     * @param types  the types of fish that can spawn, chosen at random.
     * @param edges  where fishes enter and leave; they swim at a random height inside its area.
     * @param delays how long to wait between two spawns.
     * @param random the source of randomness.
     * @throws IllegalArgumentException if there are no types.
     */
    public FishSpawner(List<FishType> types, LakeEdges edges, DurationRange delays, Random random) {
        if (types.isEmpty()) {
            throw new IllegalArgumentException("at least one fish type is required");
        }
        this.types = List.copyOf(types);
        this.edges = Objects.requireNonNull(edges);
        this.delays = Objects.requireNonNull(delays);
        this.random = Objects.requireNonNull(random);
    }

    /**
     * {@return a new fish of a random type, entering the water from one of its sides}
     */
    public Fish spawn() {
        return spawn(types.get(random.nextInt(types.size())));
    }

    /**
     * {@return a new fish entering the water from one of its sides}
     *
     * @param type the type of the fish to spawn, even if it is not among the common ones.
     */
    public Fish spawn(FishType type) {
        Objects.requireNonNull(type);
        Heading heading = Heading.random(random);
        double x = edges.entryX(heading);
        double y = random.nextDouble(edges.area().top(), edges.area().bottom());
        return new Fish(type, heading, x, y);
    }

    /**
     * {@return a random wait, in seconds, before the next spawn}
     */
    public double nextSpawnDelay() {
        return delays.next(random);
    }

    /**
     * {@return true if the fish has swum past one of the sides}
     *
     * @param fish a fish of the lake.
     */
    public boolean hasLeft(Fish fish) {
        return edges.hasLeft(fish);
    }
}
