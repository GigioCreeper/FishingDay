package it.unicam.cs.mpgc.rpg129382.view.menu;

import it.unicam.cs.mpgc.rpg129382.persistence.GameSession;
import it.unicam.cs.mpgc.rpg129382.view.navigation.GameScreen;
import it.unicam.cs.mpgc.rpg129382.view.navigation.ScreenNavigator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class MainMenuController {
    @FXML
    private Button continueButton;
    @FXML
    private Button cancelButton;
    @FXML
    private StackPane confirmOverlay;
    private final ScreenNavigator screenNavigator;
    private final GameSession session;

    public MainMenuController(ScreenNavigator screenNavigator, GameSession session) {
        this.screenNavigator = Objects.requireNonNull(screenNavigator);
        this.session = Objects.requireNonNull(session);
    }

    @FXML
    private void initialize() {
        continueButton.setDisable(!session.canContinue());
    }

    @FXML
    private void onContinueButtonClicked() {
        session.continueGame();
        screenNavigator.navigateTo(GameScreen.FISHING);
    }

    @FXML
    private void onNewGameButtonClicked() {
        if (session.canContinue()) {
            confirmOverlay.setVisible(true);
            cancelButton.requestFocus();
            return;
        }
        startNewGame();
    }

    @FXML
    private void onExitButtonClicked() {
        Platform.exit();
    }

    @FXML
    private void onCancelNewGame() {
        confirmOverlay.setVisible(false);
    }

    @FXML
    private void onConfirmNewGame() {
        startNewGame();
    }

    private void startNewGame() {
        session.newGame();
        screenNavigator.navigateTo(GameScreen.FISHING);
    }
}