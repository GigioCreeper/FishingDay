package it.unicam.cs.mpgc.rpg129382.view;

import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.Objects;


public class FishingView extends Pane {
    private final Canvas canvas;
    private final Image backgroundImage;
    public FishingView() {
        this.canvas = new Canvas(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        this.backgroundImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/lake.png"),
                "Background image not found"));
        this.getChildren().add(canvas);
        draw();
    }

    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(backgroundImage, 0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
