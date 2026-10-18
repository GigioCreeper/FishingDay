package it.unicam.cs.mpgc.rpg129382;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Fishing Day");
        stage.setScene(scene);
        stage.show();
    }
    static void main(String[] args) {
        launch(args);
    }
}
