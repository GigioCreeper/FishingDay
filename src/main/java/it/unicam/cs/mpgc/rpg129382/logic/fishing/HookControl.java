package it.unicam.cs.mpgc.rpg129382.logic.fishing;

import it.unicam.cs.mpgc.rpg129382.model.Area;
import it.unicam.cs.mpgc.rpg129382.model.Displacement;
import it.unicam.cs.mpgc.rpg129382.model.Fish;
import it.unicam.cs.mpgc.rpg129382.model.Movable;
import it.unicam.cs.mpgc.rpg129382.model.Positioned;

import java.util.Objects;

/**
 * Moves the hook: freely when there is no fish on it, by the given offset during a fight,
 * and always able to bring it back inside the water.
 */
public final class HookControl {
    private final Movable hook;
    private final double speed;
    private final Area water;

    /**
     * Creates the control of a hook.
     *
     * @param hook  the hook to move.
     * @param speed how fast the free hook moves, in pixels per second.
     * @param water the area the hook is kept inside.
     * @throws IllegalArgumentException if the speed is not positive.
     */
    public HookControl(Movable hook, double speed, Area water) {
        if (speed <= 0) {
            throw new IllegalArgumentException("speed must be positive");
        }
        this.hook = Objects.requireNonNull(hook);
        this.speed = speed;
        this.water = Objects.requireNonNull(water);
    }

    /**
     * {@return the hook, which can only be observed from outside}
     */
    public Positioned getHook() {
        return hook;
    }

    /**
     * Moves the free hook the way the player is pushing it, without letting it leave the water.
     *
     * @param directionX -1 for left, 1 for right, 0 for neither.
     * @param directionY -1 for up, 1 for down, 0 for neither.
     * @param delta      the seconds elapsed since the last tick.
     */
    public void moveFree(double directionX, double directionY, double delta) {
        double distance = speed * delta;
        hook.move(directionX * distance, directionY * distance);
        keepInWater();
    }

    /**
     * Moves the hook by the given offset, even outside the water.
     *
     * @param displacement how far the hook moves.
     */
    public void move(Displacement displacement) {
        hook.move(displacement.dx(), displacement.dy());
    }

    /**
     * Brings the hook back to the nearest point inside the water, if it has left it.
     */
    public void keepInWater() {
        hook.move(water.clampX(hook.getX()) - hook.getX(), water.clampY(hook.getY()) - hook.getY());
    }

    /**
     * Moves the fish onto the hook, so they stay together.
     *
     * @param fish the hooked fish.
     */
    public void attach(Fish fish) {
        fish.move(hook.getX() - fish.getX(), hook.getY() - fish.getY());
    }
}