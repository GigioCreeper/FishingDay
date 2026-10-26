package it.unicam.cs.mpgc.rpg129382.logic.fight;

import it.unicam.cs.mpgc.rpg129382.model.Fish;

import java.util.List;
import java.util.Objects;

public class Fight {
    private final Fish fish;
    private final List<FightPhase> phases;
    private int currentIndex;
    private double timeLeft;

    public Fight(Fish fish, List<FightPhase> phases) {
        this.fish = Objects.requireNonNull(fish);
        Objects.requireNonNull(phases);
        if (phases.isEmpty()) {
            throw new IllegalArgumentException("a fight needs at least one phase");
        }
        this.phases = List.copyOf(phases);
        enterPhase(0);
    }

    public Fish getFish() {
        return fish;
    }

    public boolean playerControls() {
        return currentPhase().playerControls();
    }

    public boolean keepsHookInWater() {
        return currentPhase().keepsHookInWater();
    }

    public Displacement step(double directionX, double directionY, double delta) {
        return currentPhase().step(fish, directionX, directionY, delta);
    }

    public void update(double delta) {
        timeLeft -= delta;
        if (timeLeft <= 0) {
            enterPhase((currentIndex + 1) % phases.size());
        }
    }

    private FightPhase currentPhase() {
        return phases.get(currentIndex);
    }

    private void enterPhase(int index) {
        currentIndex = index;
        timeLeft = currentPhase().duration();
        currentPhase().enter(fish);
    }
}