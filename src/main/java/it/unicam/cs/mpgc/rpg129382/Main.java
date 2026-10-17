package it.unicam.cs.mpgc.rpg129382;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        Scene scene = new Scene(root);
        stage.setTitle("Fishing Day");
        stage.setScene(scene);
        stage.show();
    }
    static void main(String[] args) {
        launch(args);
    }
}
