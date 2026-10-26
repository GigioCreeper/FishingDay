package it.unicam.cs.mpgc.rpg129382.view.utils;

import javafx.scene.text.Font;

import java.net.URL;
import java.util.Objects;

public final class FontLoader {

    private FontLoader() {
    }

    public static Font loadFont(String path, double size) {
        Objects.requireNonNull(path);
        URL resource = FontLoader.class.getResource(path);
        if (resource == null) {
            throw new IllegalArgumentException("Font not found: " + path);
        }
        Font font = Font.loadFont(resource.toExternalForm(), size);
        if (font == null) {
            throw new IllegalArgumentException("Invalid font file: " + path);
        }
        return font;
    }
}
