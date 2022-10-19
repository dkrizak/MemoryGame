package com.example.memorygame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MemoryGame extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MemoryGame.class.getResource("memorygame-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 800);
        stage.setTitle("Memory Game");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image("C:\\DEV\\Java Projects\\MemoryGame\\src\\main\\java\\com\\example\\memorygame\\pictures\\logo.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}