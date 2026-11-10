package it.unicam.cs.mpgc.rpg129382.view.navigation;

/**
 * Changes the screen shown to the player.
 */
public interface ScreenNavigator {

    /**
     * Navigates to the specified game screen.
     *
     * @param screen the game screen to navigate to.
     */
    void navigateTo(GameScreen screen);
}
