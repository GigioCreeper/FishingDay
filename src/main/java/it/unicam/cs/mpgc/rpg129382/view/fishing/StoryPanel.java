package it.unicam.cs.mpgc.rpg129382.view.fishing;

import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Objects;

/**
 * A parchment shown over the lake with the message found in a bottle.
 * Its look is defined in Story.css.
 */
final class StoryPanel extends VBox {
    private static final double WIDTH = 760;
    private static final double TOP = 110;

    private final Label text = new Label();
    private final Button continueButton = new Button("Continua");

    /**
     * @param onContinue what to do when the player closes the message.
     */
    StoryPanel(Runnable onContinue) {
        Objects.requireNonNull(onContinue);
        getStylesheets().addAll(stylesheet("/css/MainMenu.css"), stylesheet("/css/Story.css"));
        getStyleClass().add("story-panel");
        setPrefWidth(WIDTH);
        setLayoutX((GameConfig.WINDOW_WIDTH - WIDTH) / 2);
        setLayoutY(TOP);

        Label title = new Label("Un messaggio nella bottiglia");
        title.getStyleClass().add("story-title");
        text.getStyleClass().add("story-text");
        text.setWrapText(true);
        continueButton.getStyleClass().add("main-button");
        continueButton.setMnemonicParsing(false);
        continueButton.setOnAction(_ -> onContinue.run());

        getChildren().addAll(title, text, continueButton);
        setVisible(false);
    }

    /**
     * Shows the panel with the given message.
     * @param message the message to show.
     */
    void show(String message) {
        text.setText(Objects.requireNonNull(message));
        if (!isVisible()) {
            setVisible(true);
            continueButton.requestFocus();
        }
    }

    /**
     * Hides the panel.
     */
    void hide() {
        setVisible(false);
    }

    private static String stylesheet(String path) {
        return Objects.requireNonNull(StoryPanel.class.getResource(path), "Stylesheet not found: " + path)
                .toExternalForm();
    }
}