package it.unicam.cs.mpgc.rpg129382.view.utils;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;

/**
 * A canvas with a fixed virtual size that keeps its drawing sharp when scaled.
 * Its backing canvas always matches the real on-screen pixel size, while drawing
 * code keeps using the virtual coordinates.
 */
public final class SharpCanvas extends Pane {
    private final double virtualWidth;
    private final double virtualHeight;
    private final Canvas canvas;
    private final Scale inverseScale = new Scale(1, 1, 0, 0);
    private double resolution = 1;

    /**
     * Creates a new SharpCanvas with the specified virtual size.
     *
     * @param virtualWidth  the width used by drawing code.
     * @param virtualHeight the height used by drawing code.
     */
    public SharpCanvas(double virtualWidth, double virtualHeight) {
        this.virtualWidth = virtualWidth;
        this.virtualHeight = virtualHeight;
        this.canvas = new Canvas(virtualWidth, virtualHeight);
        canvas.getTransforms().add(inverseScale);
        getChildren().add(canvas);
        localToSceneTransformProperty().addListener((_, _, transform) -> updateResolution(transform));
    }

    /**
     * Prepares the canvas for a new frame.
     *
     * @return a GraphicsContext that accepts virtual coordinates.
     */
    public GraphicsContext beginFrame() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setTransform(resolution, 0, 0, resolution, 0, 0);
        return gc;
    }

    private void updateResolution(Transform transform) {
        double newResolution = transform.getMxx();
        if (newResolution <= 0 || newResolution == resolution) {
            return;
        }
        resolution = newResolution;
        canvas.setWidth(virtualWidth * resolution);
        canvas.setHeight(virtualHeight * resolution);
        inverseScale.setX(1 / resolution);
        inverseScale.setY(1 / resolution);
    }
}