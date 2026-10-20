package it.unicam.cs.mpgc.rpg129382.view.menu;

import it.unicam.cs.mpgc.rpg129382.view.navigation.GameScreen;
import it.unicam.cs.mpgc.rpg129382.view.navigation.ScreenNavigator;
import javafx.fxml.FXML;
import java.util.Objects;

public class MainMenuController {
    private ScreenNavigator screenNavigator;

    public void setScreenNavigator(ScreenNavigator screenNavigator) {
        this.screenNavigator = Objects.requireNonNull(screenNavigator);
    }

    @FXML
    private void onPlayButtonClicked() {
        screenNavigator.navigateTo(GameScreen.FISHING);
    }
    @FXML
    private void onAudioButtonClicked() {
        System.out.println("Audio button clicked");
        //TODO: Implement audio settings functionality
    }
}
