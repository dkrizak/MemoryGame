package com.example.memorygame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class MemoryGameController {

    @FXML
    private Button playAgainButton;
    @FXML
    private AnchorPane pane;
    @FXML
    private Label numberOfAttempts;
    @FXML
    private Label highScoreLabel;
    private String[] imgSources = {
            "src/main/java/com/example/memorygame/pictures/building.jpg",
            "src/main/java/com/example/memorygame/pictures/coin.jpg",
            "src/main/java/com/example/memorygame/pictures/computer.jpg",
            "src/main/java/com/example/memorygame/pictures/dollar.jpg",
            "src/main/java/com/example/memorygame/pictures/folder.jpg",
            "src/main/java/com/example/memorygame/pictures/home.jpg",
            "src/main/java/com/example/memorygame/pictures/microphone.jpg",
            "src/main/java/com/example/memorygame/pictures/pen.jpg",
            "src/main/java/com/example/memorygame/pictures/search.jpg",
            "src/main/java/com/example/memorygame/pictures/train.jpg"
    };

    private Image[] images = new Image[20];
    private Image[] randomImages = new Image[images.length];
    private Image background;

    private ImageView openedImage, previousImage;

    private boolean gameStarted = false;
    private boolean openedCard = false;
    private int clickCount = 0;
    private int numberOfMatches = 0;
    private int attempts = 0;
    private int highScore = Integer.MAX_VALUE;
    private DropShadow dropShadow = new DropShadow();
    
    @FXML
    public void initialize() {

        try {
            InputStream stream = new FileInputStream("src/main/java/com/example/memorygame/pictures/background.jpg");
            background = new Image(stream);

            for (int i = 0; i < pane.getChildren().size(); i++) {
                if (pane.getChildren().get(i) instanceof ImageView) {
                    ((ImageView)pane.getChildren().get(i)).setImage(background);
                    pane.getChildren().get(i).setOnMouseClicked(this::imgClick);
                }
            }

            Image image = null;
            int j = 0;
            for (int i = 0; i < images.length; i++) {

                if (i % 2 == 0) {
                    stream = new FileInputStream(imgSources[j]);
                    image = new Image(stream);
                    j++;
                }

                images[i] = image;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void imgClick(MouseEvent event) {

        if (!gameStarted) {
            randomizeImages();
            gameStarted = true;
        }

        ImageView imageView = (ImageView) event.getSource();

        if (clickCount == 2) {
            clickCount = 0;
            openedCard = false;
            openedImage.setMouseTransparent(false);
            openedImage.setImage(background);
            openedImage.setEffect(null);
            previousImage.setMouseTransparent(false);
            previousImage.setImage(background);
            previousImage.setEffect(null);
            return;
        }

        int colorIndex = Integer.parseInt(imageView.getId());

        if (openedCard) {
            imageView.setMouseTransparent(true);
            imageView.setImage(randomImages[colorIndex]);
            imageView.setEffect(dropShadow);
            attempts++;
            numberOfAttempts.setText("Number of Attempts: " + attempts);
            if (randomImages[colorIndex].equals(randomImages[Integer.parseInt(openedImage.getId())])){
                openedCard = false;
                clickCount = 0;
                numberOfMatches++;
            } else {
                clickCount = 2;
                previousImage = openedImage;
                openedImage = imageView;
            }
        } else {
            imageView.setMouseTransparent(true);
            imageView.setImage(randomImages[colorIndex]);
            imageView.setEffect(dropShadow);
            openedImage = imageView;
            openedCard = true;
        }

        if (numberOfMatches == 10) {
            playAgainButton.setOpacity(1);
            if (attempts < highScore) {
                highScore = attempts;
                highScoreLabel.setText("High Score: " + highScore);
            }
        }
    }
    protected void randomizeImages() {
        int i = 0;
        int num = 0;
        ArrayList<Integer> numbers = new ArrayList<>();

        while (i < images.length) {
            num = (int)(Math.random() * images.length);
            if (!numbers.contains(num)) {
                randomImages[i] = images[num];
                numbers.add(num);
                i++;
            }
        }
    }

    public void playAgain() {

        for (int i = 0; i < pane.getChildren().size(); i++) {
            if (pane.getChildren().get(i) instanceof ImageView) {
                ((ImageView) pane.getChildren().get(i)).setImage(background);
                pane.getChildren().get(i).setEffect(null);
                pane.getChildren().get(i).setMouseTransparent(false);
            }
        }
        gameStarted = false;
        playAgainButton.setOpacity(0);
        numberOfMatches = 0;
        attempts = 0;
        numberOfAttempts.setText("Number of Attempts: " + attempts);
    }
    public void closeGame() {
        System.exit(0);
    }
}