package it.unicam.cs.mpgc.rpg129382.view.window;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Toggles the full screen mode of a stage when a specific key is pressed.
 */
public final class FullScreenToggle implements EventHandler<KeyEvent> {
    private final Stage stage;
    private final KeyCode toggleKey;

    /**
     * Creates a new FullScreenToggle.
     * @param stage the stage whose full screen mode is toggled.
     * @param toggleKey the key that toggles full screen.
     */
    public FullScreenToggle(Stage stage, KeyCode toggleKey) {
        this.stage = Objects.requireNonNull(stage);
        this.toggleKey = Objects.requireNonNull(toggleKey);
    }

    /**
     * Switches the stage between windowed and full screen mode
     * when the toggle key is pressed.
     * @param event the key event to handle.
     */
    @Override
    public void handle(KeyEvent event) {
        if (event.getCode() == toggleKey) {
            stage.setFullScreen(!stage.isFullScreen());
            event.consume();
        }
    }
}