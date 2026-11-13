package it.unicam.cs.mpgc.rpg129382.view.fishing;

import it.unicam.cs.mpgc.rpg129382.logic.fishing.FishingState;
import it.unicam.cs.mpgc.rpg129382.view.utils.resources.FontLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Objects;

final class FishingHud {
    private static final Font FONT = FontLoader.loadFont("/fonts/PixelifySans.ttf", 32);
    private static final double X = 30;
    private static final double Y = 50;
    private static final double LINE_HEIGHT = 40;

    private final FishingState state;

    FishingHud(FishingState state) {
        this.state = Objects.requireNonNull(state);
    }

    void draw(GraphicsContext gc) {
        gc.setFont(FONT);
        drawLine(gc, "Fish caught: " + state.getInventoryCount(), 0);
        drawLine(gc, "Coins: " + state.getMoney(), 1);
        if (state.isFishPulling()) {
            drawLine(gc, "The fish is pulling!", 2);
        }
    }

    private void drawLine(GraphicsContext gc, String text, int line) {
        double y = Y + line * LINE_HEIGHT;
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(4);
        gc.strokeText(text, X, y);
        gc.setFill(Color.WHITE);
        gc.fillText(text, X, y);
    }
}