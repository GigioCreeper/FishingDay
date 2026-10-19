package it.unicam.cs.mpgc.rpg129382;

import it.unicam.cs.mpgc.rpg129382.view.*;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.stage.Stage;
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        StageNavigator navigator = new StageNavigator(stage);

        navigator.register(GameScreen.MAIN_MENU,new MainMenuScreenFactory(navigator));
        navigator.register(GameScreen.FISHING, () -> new Label("Fishing Screen"));

        stage.setTitle("Fishing Day");
        navigator.navigateTo(GameScreen.MAIN_MENU);
        stage.show();
    }


    static void main(String[] args) {
        launch(args);
    }
}