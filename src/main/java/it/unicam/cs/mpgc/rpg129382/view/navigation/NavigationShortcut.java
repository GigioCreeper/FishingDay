package it.unicam.cs.mpgc.rpg129382.view.navigation;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Objects;

/**
 * Navigates to a game screen when a specific key is pressed.
 */
public final class NavigationShortcut implements EventHandler<KeyEvent> {
    private final KeyCode key;
    private final ScreenNavigator navigator;
    private final GameScreen target;

    /**
     * Creates a new NavigationShortcut.
     *
     * @param key       the key that triggers the navigation.
     * @param navigator the navigator used to change screen.
     * @param target    the screen to navigate to.
     */
    public NavigationShortcut(KeyCode key, ScreenNavigator navigator, GameScreen target) {
        this.key = Objects.requireNonNull(key);
        this.navigator = Objects.requireNonNull(navigator);
        this.target = Objects.requireNonNull(target);
    }

    /**
     * Handles the key event. If the pressed key matches the specified key, it navigates to the target screen.
     *
     * @param event the key event to handle.
     */
    @Override
    public void handle(KeyEvent event) {
        if (event.getCode() == key) {
            navigator.navigateTo(target);
            event.consume();
        }
    }
}