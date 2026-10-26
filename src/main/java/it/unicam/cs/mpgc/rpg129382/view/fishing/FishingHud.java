package it.unicam.cs.mpgc.rpg129382.view.fishing;

import it.unicam.cs.mpgc.rpg129382.logic.fishing.FishingState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Objects;

final class FishingHud {
    private static final Font FONT = Font.font("Arial", FontWeight.BOLD, 32);
    private static final double X = 30;
    private static final double Y = 50;
    private static final double LINE_HEIGHT = 40;

    private final FishingState state;

    FishingHud(FishingState state) {
        this.state = Objects.requireNonNull(state);
    }

    void draw(GraphicsContext gc) {
        gc.setFont(FONT);
        drawLine(gc, "Pesci pescati: " + state.getInventoryCount(), Y);
        if (state.isFishPulling()) {
            drawLine(gc, "Il pesce tira!", Y + LINE_HEIGHT);
        }
    }

    private void drawLine(GraphicsContext gc, String text, double y) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(4);
        gc.strokeText(text, X, y);
        gc.setFill(Color.WHITE);
        gc.fillText(text, X, y);
    }
}