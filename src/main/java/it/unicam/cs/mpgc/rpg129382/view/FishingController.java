package it.unicam.cs.mpgc.rpg129382.view;

import it.unicam.cs.mpgc.rpg129382.logic.FishingGame;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public class FishingController {
    private final FishingGame game;
    private final Set<KeyCode> pressedKeys = EnumSet.noneOf(KeyCode.class);

    public FishingController(FishingGame game) {
        this.game = Objects.requireNonNull(game);
    }

    public void onKeyPressed(KeyEvent keyCode) {
        pressedKeys.add(keyCode.getCode());
        updateDirection();
    }

    public void onKeyReleased(KeyEvent keyCode) {
        pressedKeys.remove(keyCode.getCode());
        updateDirection();
    }

    private void updateDirection() {
        double directionX = axis(KeyCode.RIGHT, KeyCode.LEFT);
        double directionY = axis(KeyCode.DOWN, KeyCode.UP);
        game.SetDirection(directionX, directionY);
    }

    private double axis(KeyCode positive, KeyCode negative) {
        double value = 0;
        if (pressedKeys.contains(positive)) {
            value += 1;
        }
        if (pressedKeys.contains(negative)) {
            value -= 1;
        }
        return value;
    }
}
