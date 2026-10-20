package it.unicam.cs.mpgc.rpg129382.logic;

import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.model.Movable;

import java.util.Objects;

public class FishingGame {
    private final Movable hook;
    private double directionX;
    private double directionY;

    public FishingGame(Movable hook) {
        this.hook = Objects.requireNonNull(hook);
    }

    public Movable getHook() {
        return hook;
    }

    public void SetDirection(double directionX, double directionY) {
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public void update(double delta) {
        double distance = GameConfig.HOOK_SPEED * delta;
        hook.move(directionX * distance, directionY * distance);
        keepHookInBounds();
    }

    private void keepHookInBounds() {
        //TODO: implement this method to keep the hook within the LAKE bounds instead of the window bounds
        double x = Math.clamp(hook.getX(), 0, GameConfig.WINDOW_WIDTH);
        double y = Math.clamp(hook.getY(), 0, GameConfig.WINDOW_HEIGHT);
        hook.move(x - hook.getX(), y - hook.getY());
    }
}
