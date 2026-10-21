package it.unicam.cs.mpgc.rpg129382.view.fishing;

import it.unicam.cs.mpgc.rpg129382.model.Inventory;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Objects;

final class FishingHud {
    private static final Font FONT = Font.font("Arial", FontWeight.BOLD, 32);
    private static final double X = 30;
    private static final double Y = 50;

    private final Inventory inventory;

    FishingHud(Inventory inventory) {
        this.inventory = Objects.requireNonNull(inventory);
    }

    void draw(GraphicsContext gc) {
        String text = "Pesci pescati: " + inventory.totalCount();
        gc.setFont(FONT);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(4);
        gc.strokeText(text, X, Y);
        gc.setFill(Color.WHITE);
        gc.fillText(text, X, Y);
    }
}