package it.unicam.cs.mpgc.rpg129382.view.menu;

import it.unicam.cs.mpgc.rpg129382.persistence.GameSession;
import it.unicam.cs.mpgc.rpg129382.view.navigation.GameScreen;
import it.unicam.cs.mpgc.rpg129382.view.navigation.ScreenNavigator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.Objects;

public class MainMenuController {
    @FXML
    private Button continueButton;

    private ScreenNavigator screenNavigator;
    private GameSession session;

    public void setScreenNavigator(ScreenNavigator screenNavigator) {
        this.screenNavigator = Objects.requireNonNull(screenNavigator);
    }

    public void setGameSession(GameSession session) {
        this.session = Objects.requireNonNull(session);
        continueButton.setDisable(!session.canContinue());
    }

    @FXML
    private void onContinueButtonClicked() {
        session.continueGame();
        screenNavigator.navigateTo(GameScreen.FISHING);
    }

    @FXML
    private void onNewGameButtonClicked() {
        if (session.canContinue() && !confirmNewGame()) {
            return;
        }
        session.newGame();
        screenNavigator.navigateTo(GameScreen.FISHING);
    }

    @FXML
    private void onExitButtonClicked() {
        Platform.exit();
    }

    private static boolean confirmNewGame() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Your saved game will be lost. Start a new game?");
        alert.setHeaderText(null);
        return alert.showAndWait().filter(ButtonType.OK::equals).isPresent();
    }
}