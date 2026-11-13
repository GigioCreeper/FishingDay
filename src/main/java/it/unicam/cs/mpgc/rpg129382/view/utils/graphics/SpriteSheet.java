package it.unicam.cs.mpgc.rpg129382.view.utils.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 * An image split into a grid of frames of the same size, drawn one at a time to animate a sprite.
 * Frames are numbered row by row, starting from the top left corner.
 */
public final class SpriteSheet {
    private final Image image;
    private final int columns;
    private final int rows;
    private final double frameWidth;
    private final double frameHeight;

    /**
     * Creates a sprite sheet from an image split into a grid of frames.
     *
     * @param image   the image that holds every frame.
     * @param columns how many frames there are in each row.
     * @param rows    how many rows of frames there are.
     * @throws IllegalArgumentException if columns or rows are not positive.
     */
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

    /**
     * Draws one frame, keeping its proportions.
     *
     * @param gc      where to draw.
     * @param frame   the number of the frame; it wraps around, so any number, even negative, is valid.
     * @param centerX the horizontal position of the center of the frame.
     * @param centerY the vertical position of the center of the frame.
     * @param width   how wide to draw the frame; the height follows from it.
     * @param flipped true to mirror the frame horizontally.
     */
    public void drawFrame(GraphicsContext gc, int frame, double centerX, double centerY,
                          double width, boolean flipped) {
        int index = Math.floorMod(frame, columns * rows);
        int column = index % columns;
        int row = index / columns;
        double sourceX = column * frameWidth;
        double sourceY = row * frameHeight;
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