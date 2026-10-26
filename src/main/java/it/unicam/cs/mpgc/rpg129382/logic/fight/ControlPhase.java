package it.unicam.cs.mpgc.rpg129382.logic.fight;

import it.unicam.cs.mpgc.rpg129382.model.Displacement;
import it.unicam.cs.mpgc.rpg129382.model.Fish;
import it.unicam.cs.mpgc.rpg129382.model.Heading;

import java.util.Objects;
import java.util.Random;

public final class ControlPhase implements FightPhase {
    private final Random random;
    private final DurationRange durations;
    private final double hookedSpeed;

    public ControlPhase(Random random, DurationRange durations, double hookedSpeed) {
        if (hookedSpeed <= 0) {
            throw new IllegalArgumentException("hookedSpeed must be positive");
        }
        this.random = Objects.requireNonNull(random);
        this.durations = Objects.requireNonNull(durations);
        this.hookedSpeed = hookedSpeed;
    }

    @Override
    public boolean isFishPulling() {
        return false;
    }

    @Override
    public boolean keepsHookInWater() {
        return true;
    }

    @Override
    public double nextDuration() {
        return durations.next(random);
    }

    @Override
    public Displacement step(Fish fish, double directionX, double directionY, double delta) {
        if (directionX > 0) {
            fish.face(Heading.RIGHT);
        } else if (directionX < 0) {
            fish.face(Heading.LEFT);
        }
        double distance = hookedSpeed * delta;
        return new Displacement(directionX * distance, directionY * distance);
    }
}
