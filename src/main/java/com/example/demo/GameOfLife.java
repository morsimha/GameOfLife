/**
 * Maman 11, question 2.
 * Mor Simha, 206029993.
 */


package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameOfLife extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameOfLife.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 503, 540);
        stage.setTitle("Game of Life");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}