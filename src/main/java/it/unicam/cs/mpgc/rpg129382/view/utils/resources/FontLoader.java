package it.unicam.cs.mpgc.rpg129382.view.utils.resources;

import javafx.scene.text.Font;

import java.net.URL;
import java.util.Objects;

/**
 * Loads the fonts bundled with the game.
 */
public final class FontLoader {

    private FontLoader() {
    }

    /**
     * {@return the loaded font}
     *
     * @param path the path of the font file on the classpath, e.g. "/fonts/PixelifySans.ttf".
     * @param size the font size, in points.
     * @throws IllegalArgumentException if the file is not found or is not a valid font.
     */
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
