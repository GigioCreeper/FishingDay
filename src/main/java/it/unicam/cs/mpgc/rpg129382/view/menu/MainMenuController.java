package it.unicam.cs.mpgc.rpg129382.view.menu;

import it.unicam.cs.mpgc.rpg129382.logic.session.GameSession;
import it.unicam.cs.mpgc.rpg129382.view.navigation.GameScreen;
import it.unicam.cs.mpgc.rpg129382.view.navigation.ScreenNavigator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.util.Objects;

/**
 * Controller of the main menu: starts a new game or continues the saved one,
 * asking for confirmation before a new game overwrites a save.
 */
public class MainMenuController {
    @FXML
    private Button continueButton;
    @FXML
    private Button cancelButton;
    @FXML
    private StackPane confirmOverlay;
    private final ScreenNavigator screenNavigator;
    private final GameSession session;

    /**
     * Creates the controller of the main menu.
     *
     * @param screenNavigator the navigator used to go fishing.
     * @param session         the game session the menu starts or continues.
     */
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