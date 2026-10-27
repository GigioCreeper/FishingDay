package it.unicam.cs.mpgc.rpg129382;

import it.unicam.cs.mpgc.rpg129382.config.RodCatalog;
import it.unicam.cs.mpgc.rpg129382.logic.shop.Shop;
import it.unicam.cs.mpgc.rpg129382.model.Player;
import it.unicam.cs.mpgc.rpg129382.view.fishing.FishingScreenFactory;
import it.unicam.cs.mpgc.rpg129382.view.menu.MainMenuScreenFactory;
import it.unicam.cs.mpgc.rpg129382.view.navigation.GameScreen;
import it.unicam.cs.mpgc.rpg129382.view.navigation.StageNavigator;
import it.unicam.cs.mpgc.rpg129382.view.shop.ShopScreenFactory;
import it.unicam.cs.mpgc.rpg129382.view.window.FullScreenToggle;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        StageNavigator navigator = new StageNavigator(stage);
        Player player = new Player(RodCatalog.starter());
        Shop shop = new Shop();
        navigator.register(GameScreen.MAIN_MENU, new MainMenuScreenFactory(navigator));
        navigator.register(GameScreen.FISHING, new FishingScreenFactory(player, navigator));
        navigator.register(GameScreen.SHOP, new ShopScreenFactory(shop, player, navigator));

        stage.setTitle("Fishing Day");
        stage.setResizable(false);
        stage.addEventFilter(KeyEvent.KEY_PRESSED, new FullScreenToggle(stage, KeyCode.F11));

        navigator.navigateTo(GameScreen.MAIN_MENU);
        stage.show();
    }


    static void main(String[] args) {
        launch(args);
    }
}