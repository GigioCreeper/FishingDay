package it.unicam.cs.mpgc.rpg129382.view.menu;

import it.unicam.cs.mpgc.rpg129382.view.navigation.ScreenFactory;
import it.unicam.cs.mpgc.rpg129382.view.navigation.ScreenNavigator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

public final class MainMenuScreenFactory implements ScreenFactory {
    private final ScreenNavigator navigator;


    /**
     * Creates a new MainMenuScreenFactory with the specified ScreenNavigator.
     * @param navigator the ScreenNavigator to use for navigation.
     */
    public MainMenuScreenFactory(ScreenNavigator navigator) {
        this.navigator = Objects.requireNonNull(navigator);
    }


    /**
     * Creates and returns the content of the main menu screen.
     * @return the Parent node representing the main menu screen.
     * @throws UncheckedIOException if there is an error loading the FXML file.
     */
    @Override
    public Parent createScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
            Parent root = loader.load();
            MainMenuController controller = loader.getController();
            controller.setScreenNavigator(navigator);
            return root;
        } catch (IOException e) {
            throw new UncheckedIOException("Could not load MainMenu.fxml", e);
        }
    }
}