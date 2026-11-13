package it.unicam.cs.mpgc.rpg129382.logic.fishing.fight;

import it.unicam.cs.mpgc.rpg129382.logic.Updatable;
import it.unicam.cs.mpgc.rpg129382.model.geometry.Displacement;
import it.unicam.cs.mpgc.rpg129382.model.Fish;
import it.unicam.cs.mpgc.rpg129382.model.Lake;
import it.unicam.cs.mpgc.rpg129382.model.geometry.Positioned;

import java.util.List;
import java.util.Objects;

/**
 * A fight with a hooked fish: cycles through its phases, each lasting a random time,
 * and decides when the fight ends.
 */
public class Fight implements Updatable {
    private final Fish fish;
    private final List<FightPhase> phases;
    private int currentIndex;
    private double timeLeft;

    /**
     * Starts the fight from the first phase.
     *
     * @param fish   the hooked fish.
     * @param phases the phases of the fight, in the order they repeat.
     * @throws IllegalArgumentException if there are no phases.
     */
    public Fight(Fish fish, List<FightPhase> phases) {
        this.fish = Objects.requireNonNull(fish);
        Objects.requireNonNull(phases);
        if (phases.isEmpty()) {
            throw new IllegalArgumentException("a fight needs at least one phase");
        }
        this.phases = List.copyOf(phases);
        enterPhase(0);
    }

    /**
     * {@return the hooked fish}
     */
    public Fish getFish() {
        return fish;
    }

    /**
     * {@return true if, in the current phase, the fish is pulling and the player has no control}
     */
    public boolean isFishPulling() {
        return currentPhase().isFishPulling();
    }

    /**
     * {@return true if, in the current phase, the hook must be kept inside the water}
     */
    public boolean keepsHookInWater() {
        return currentPhase().keepsHookInWater();
    }

    /**
     * Asks the current phase how the hook moves in this tick.
     *
     * @param directionX the horizontal direction pressed by the player.
     * @param directionY the vertical direction pressed by the player.
     * @param delta      the seconds elapsed since the last tick.
     * @return how far the hook moves in this tick.
     */
    public Displacement step(double directionX, double directionY, double delta) {
        return currentPhase().step(fish, directionX, directionY, delta);
    }

    /**
     * Lets time pass, and moves on to the next phase when the current one is over;
     * after the last phase the first one comes back.
     *
     * @param delta the seconds elapsed since the last tick.
     */
    @Override
    public void update(double delta) {
        timeLeft -= delta;
        if (timeLeft <= 0) {
            enterPhase((currentIndex + 1) % phases.size());
        }
    }

    /**
     * Decides how the fight is going: the line breaks if the hook has left the water,
     * the fish is caught if it has reached the catch zone.
     *
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
