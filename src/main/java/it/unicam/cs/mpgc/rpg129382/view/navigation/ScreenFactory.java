package it.unicam.cs.mpgc.rpg129382.view.navigation;

import javafx.scene.Parent;

/**
 * Creates the content of a game screen.
 */
public interface ScreenFactory {

    /**
     * Creates the content of a game screen. It is called on every navigation,
     * so each visit gets a fresh screen.
     *
     * @return the Parent node representing the screen.
     */
    Parent createScreen();
}
