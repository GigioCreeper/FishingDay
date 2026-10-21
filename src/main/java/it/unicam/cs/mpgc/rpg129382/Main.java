package it.unicam.cs.mpgc.rpg129382;

import it.unicam.cs.mpgc.rpg129382.model.Player;
import it.unicam.cs.mpgc.rpg129382.view.fishing.FishingScreenFactory;
import it.unicam.cs.mpgc.rpg129382.view.menu.MainMenuScreenFactory;
import it.unicam.cs.mpgc.rpg129382.view.navigation.GameScreen;
import it.unicam.cs.mpgc.rpg129382.view.navigation.StageNavigator;
import javafx.application.Application;
import javafx.stage.Stage;
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        StageNavigator navigator = new StageNavigator(stage);
        Player player = new Player();
        navigator.register(GameScreen.MAIN_MENU,new MainMenuScreenFactory(navigator));
        navigator.register(GameScreen.FISHING, new FishingScreenFactory(player.getInventory()));

        stage.setTitle("Fishing Day");
        navigator.navigateTo(GameScreen.MAIN_MENU);
        stage.show();
    }


    static void main(String[] args) {
        launch(args);
    }
}