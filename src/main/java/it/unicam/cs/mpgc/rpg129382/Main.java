package it.unicam.cs.mpgc.rpg129382;

import it.unicam.cs.mpgc.rpg129382.view.GameScreen;
import it.unicam.cs.mpgc.rpg129382.view.MainMenuController;
import it.unicam.cs.mpgc.rpg129382.view.StageNavigator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UncheckedIOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        StageNavigator navigator = new StageNavigator(stage);

        navigator.register(GameScreen.MAIN_MENU, () -> loadMainMenu(navigator));
        navigator.register(GameScreen.FISHING, () -> new Label("Qui ci sarà il lago"));

        stage.setTitle("Fishing Day");
        navigator.navigateTo(GameScreen.MAIN_MENU);
        stage.show();
    }

    private Parent loadMainMenu(StageNavigator navigator) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
            Parent root = loader.load();
            MainMenuController controller = loader.getController();
            controller.setScreenNavigator(navigator);
            return root;
        } catch (IOException e) {
            throw new UncheckedIOException("Impossibile caricare MainMenu.fxml", e);
        }
    }

    static void main(String[] args) {
        launch(args);
    }
}