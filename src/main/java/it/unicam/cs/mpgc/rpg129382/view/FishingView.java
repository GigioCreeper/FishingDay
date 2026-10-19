package it.unicam.cs.mpgc.rpg129382.view;

import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.model.Hook;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.Objects;

import static it.unicam.cs.mpgc.rpg129382.view.utils.ImageLoader.loadImage;

public class FishingView extends Pane {
    private final Canvas canvas;
    private final Image background;
    private final Image hookImage;
    private final Hook hook;

    public FishingView(Hook hook) {
        this.hook = Objects.requireNonNull(hook);
        this.canvas = new Canvas(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        this.background = loadImage("/images/lake.png");
        this.hookImage = loadImage("/images/hook.png");
        this.getChildren().add(canvas);
        draw();
    }
    //Maybe to put in a separate class, but for now it's fine here


    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(hookImage,
                hook.getX() - hookImage.getWidth() / 2,
                hook.getY() - hookImage.getHeight() / 2);
    }
}