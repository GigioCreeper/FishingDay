package it.unicam.cs.mpgc.rpg129382.view.fishing;

import it.unicam.cs.mpgc.rpg129382.logic.fishing.FishingCommands;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

final class FishingKeyboardInput {
    private final FishingCommands commands;
    private final Set<KeyCode> pressedKeys = EnumSet.noneOf(KeyCode.class);

    FishingKeyboardInput(FishingCommands commands) {
        this.commands = Objects.requireNonNull(commands);
    }

    void onKeyPressed(KeyEvent event) {
        pressedKeys.add(event.getCode());
        updateDirection();
    }

    void onKeyReleased(KeyEvent event) {
        pressedKeys.remove(event.getCode());
        updateDirection();
    }

    private void updateDirection() {
        commands.setDirection(
                axis(KeyCode.RIGHT, KeyCode.LEFT),
                axis(KeyCode.DOWN, KeyCode.UP));
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