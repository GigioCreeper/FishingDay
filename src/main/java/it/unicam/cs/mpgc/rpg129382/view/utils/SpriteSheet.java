package it.unicam.cs.mpgc.rpg129382.view.utils;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

public final class SpriteSheet {
    private final Image image;
    private final int columns;
    private final int rows;
    private final double frameWidth;
    private final double frameHeight;

    public SpriteSheet(Image image, int columns, int rows) {
        this.image = Objects.requireNonNull(image);
        if (columns <= 0 || rows <= 0) {
            throw new IllegalArgumentException("columns and rows must be positive");
        }
        this.columns = columns;
        this.rows = rows;
        this.frameWidth = image.getWidth() / columns;
        this.frameHeight = image.getHeight() / rows;
    }

    public void drawFrame(GraphicsContext gc, int frame, double centerX, double centerY,
                          double width, boolean flipped) {
        int index = Math.floorMod(frame, columns * rows);
        double sourceX = (index % columns) * frameWidth;
        double sourceY = (index / columns) * frameHeight;
        double height = width * frameHeight / frameWidth;

        gc.save();
        gc.translate(centerX, centerY);
        if (flipped) {
            gc.scale(-1, 1);
        }
        gc.drawImage(image,
                sourceX, sourceY, frameWidth, frameHeight,
                -width / 2, -height / 2, width, height);
        gc.restore();
    }
}