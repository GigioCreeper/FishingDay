package it.unicam.cs.mpgc.rpg129382.view.menu;

import it.unicam.cs.mpgc.rpg129382.logic.session.GameSession;
import it.unicam.cs.mpgc.rpg129382.view.navigation.ScreenFactory;
import it.unicam.cs.mpgc.rpg129382.view.navigation.ScreenNavigator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

/**
 * Creates the main menu screen from its FXML file.
 */
public final class MainMenuScreenFactory implements ScreenFactory {
    private final ScreenNavigator navigator;
    private final GameSession session;

    /**
     * Creates a new MainMenuScreenFactory.
     *
     * @param navigator the ScreenNavigator to use for navigation.
     * @param session   the game session the menu starts or continues.
     */
    public MainMenuScreenFactory(ScreenNavigator navigator, GameSession session) {
        this.navigator = Objects.requireNonNull(navigator);
        this.session = Objects.requireNonNull(session);
    }

    /**
     * Creates and returns the content of the main menu screen.
     *
     * @return the Parent node representing the main menu screen.
     * @throws UncheckedIOException if there is an error loading the FXML file.
     */
    @Override
    public Parent createScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
            loader.setControllerFactory(_ -> new MainMenuController(navigator, session));
            return loader.load();
        } catch (IOException e) {
            throw new UncheckedIOException("Could not load MainMenu.fxml", e);
        }
    }
}