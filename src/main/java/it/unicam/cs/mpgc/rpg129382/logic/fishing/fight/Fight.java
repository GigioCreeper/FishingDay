package it.unicam.cs.mpgc.rpg129382.logic.fishing.fight;

import it.unicam.cs.mpgc.rpg129382.model.Displacement;
import it.unicam.cs.mpgc.rpg129382.model.Fish;
import it.unicam.cs.mpgc.rpg129382.model.Lake;
import it.unicam.cs.mpgc.rpg129382.model.Positioned;

import java.util.List;
import java.util.Objects;

/**
 * A fight with a hooked fish: cycles through its phases, each lasting a random time,
 * and decides when the fight ends.
 */
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

    public boolean isFishPulling() {
        return currentPhase().isFishPulling();
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

    /**
     * @param hook the hook holding the fish.
     * @param lake the lake where the fight takes place.
     * @return whether the line broke, the fish was caught or the fight goes on.
     */
    public FightOutcome outcome(Positioned hook, Lake lake) {
        Objects.requireNonNull(hook);
        Objects.requireNonNull(lake);
        if (!lake.water().contains(hook.getX(), hook.getY())) {
            return FightOutcome.LINE_BROKEN;
        }
        if (lake.catchZone().contains(fish.getX(), fish.getY())) {
            return FightOutcome.CAUGHT;
        }
        return FightOutcome.ONGOING;
    }

    private FightPhase currentPhase() {
        return phases.get(currentIndex);
    }

    private void enterPhase(int index) {
        currentIndex = index;
        timeLeft = currentPhase().nextDuration();
        currentPhase().enter(fish);
    }
}
