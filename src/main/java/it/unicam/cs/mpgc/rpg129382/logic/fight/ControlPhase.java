package it.unicam.cs.mpgc.rpg129382.logic.fight;

import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.model.Fish;
import it.unicam.cs.mpgc.rpg129382.model.Heading;

import java.util.Objects;
import java.util.Random;

public final class ControlPhase implements FightPhase {
    private final Random random;

    public ControlPhase(Random random) {
        this.random = Objects.requireNonNull(random);
    }

    @Override
    public boolean playerControls() {
        return true;
    }

    @Override
    public boolean keepsHookInWater() {
        return true;
    }

    @Override
    public double duration() {
        return random.nextDouble(GameConfig.CONTROL_MIN_SECONDS, GameConfig.CONTROL_MAX_SECONDS);
    }

    @Override
    public void enter(Fish fish) {
    }

    @Override
    public Displacement step(Fish fish, double directionX, double directionY, double delta) {
        if (directionX > 0) {
            fish.face(Heading.RIGHT);
        } else if (directionX < 0) {
            fish.face(Heading.LEFT);
        }
        double distance = GameConfig.HOOKED_SPEED * delta;
        return new Displacement(directionX * distance, directionY * distance);
    }
}