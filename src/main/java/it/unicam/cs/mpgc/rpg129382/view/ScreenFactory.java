package it.unicam.cs.mpgc.rpg129382.view;

import javafx.scene.Parent;

public interface ScreenFactory {
    /**
     * Creates and returns the content of a game screen.
     * @return the Parent node representing the screen.
     */
    Parent createScreen();
}
