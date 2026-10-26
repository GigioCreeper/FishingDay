package it.unicam.cs.mpgc.rpg129382.view.window;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

/**
 * A container that keeps its content at a fixed virtual resolution
 * and scales it uniformly to fit the available space, centering it with black bars.
 */
public final class LetterboxPane extends Pane {
    private final double virtualWidth;
    private final double virtualHeight;
    private final Pane frame = new Pane();
    private final Scale scale = new Scale(1, 1, 0, 0);

    /**
     * Creates a new LetterboxPane with the specified virtual resolution.
     * @param virtualWidth the width the content is designed for.
     * @param virtualHeight the height the content is designed for.
     */
    public LetterboxPane(double virtualWidth, double virtualHeight) {
        this.virtualWidth = virtualWidth;
        this.virtualHeight = virtualHeight;
        setStyle("-fx-background-color: black;");
        frame.setPrefSize(virtualWidth, virtualHeight);
        frame.setClip(new Rectangle(virtualWidth, virtualHeight));
        frame.getTransforms().add(scale);
        getChildren().add(frame);
        widthProperty().addListener(_ -> updateScale());
        heightProperty().addListener(_ -> updateScale());
    }

    /**
     * Replaces the displayed content.
     * @param content the new content, sized to the virtual resolution.
     */
    public void setContent(Parent content) {
        if (content instanceof Region region) {
            region.setPrefSize(virtualWidth, virtualHeight);
        }
        frame.getChildren().setAll(content);
    }

    private void updateScale() {
        double factor = Math.min(getWidth() / virtualWidth, getHeight() / virtualHeight);
        scale.setX(factor);
        scale.setY(factor);
        frame.setLayoutX((getWidth() - virtualWidth * factor) / 2);
        frame.setLayoutY((getHeight() - virtualHeight * factor) / 2);
    }
}