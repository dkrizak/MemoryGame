package com.example.memorygame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class MemoryGameController {

    @FXML
    private Button playAgainButton;
    @FXML
    private AnchorPane pane;
    @FXML
    private Label numberOfAttempts;

    @FXML
    private MenuItem closeGame;

    private String[] colors = {"#006400","#006400", "#00008b", "#00008b", "#b03060", "#b03060", "#ff4500", "#ff4500", "#ffff00", "#ffff00",
                                "#deb887", "#deb887", "#00ff00", "#00ff00", "#00ffff", "#00ffff", "#ff00ff", "#ff00ff", "#6495ed", "#6495ed"};
    private String[] randomColors = new String[colors.length];

    private boolean gameStarted = false;
    private boolean openedCard = false;
    private Button openedButton, previousButton;

    private int clickCount = 0;
    private int numberOfMatches = 0;
    private int attempts = 0;
    private DropShadow dropShadow = new DropShadow();

    @FXML
    protected void cardClick(ActionEvent event) {

        if (!gameStarted) {
            randomizeColors();
            gameStarted = true;
        }

        Button btn = (Button) event.getTarget();

        if (clickCount == 2) {
            clickCount = 0;
            openedCard = false;
            //openedButton.setDisable(false);
            openedButton.setMouseTransparent(false);
            openedButton.setStyle("-fx-background-radius: 5%; -fx-border-radius: 5%");
            openedButton.setEffect(null);
            //previousButton.setDisable(false);
            previousButton.setMouseTransparent(false);
            previousButton.setStyle("-fx-background-radius: 5%; -fx-border-radius: 5%");
            previousButton.setEffect(null);
            return;
        }

        int colorIndex = Integer.parseInt(btn.getId());

        if (openedCard) {
            //btn.setDisable(true);
            btn.setMouseTransparent(true);
            btn.setStyle("-fx-background-color: " + randomColors[colorIndex] + ";-fx-background-radius: 5%; -fx-border-radius: 5%;");
            btn.setEffect(dropShadow);
            //btn.setEffect(dropShadow);
            attempts++;
            numberOfAttempts.setText("Number of Attempts: " + attempts);
            if (randomColors[colorIndex].equals(randomColors[Integer.parseInt(openedButton.getId())])){
                openedCard = false;
                clickCount = 0;
                numberOfMatches++;
            } else {
                clickCount = 2;
                previousButton = openedButton;
                openedButton = btn;
            }
        } else {
            //btn.setDisable(true);
            btn.setMouseTransparent(true);
            btn.setStyle("-fx-background-color: " + randomColors[colorIndex] + ";-fx-background-radius: 5%; -fx-border-radius: 5%");
            //btn.setStyle("-fx-graphic: url('/pictures/coin.png')");
            btn.setEffect(dropShadow);
            openedButton = btn;
            openedCard = true;
        }

        if (numberOfMatches == 10) {
            playAgainButton.setOpacity(1);
        }

    }

    protected void randomizeColors() {
        int i = 0;
        int num = 0;
        ArrayList<Integer> numbers = new ArrayList<Integer>();

        while (i < colors.length) {
            num = (int)(Math.random() * colors.length);
            if (!numbers.contains(num)) {
                randomColors[i] = colors[num];
                numbers.add(num);
                i++;
            }
        }
    }

    public void playAgain() {

        for (int i = 0; i < pane.getChildren().size(); i++) {
            if (pane.getChildren().get(i) instanceof Button && pane.getChildren().get(i).getId().length() < 3) {
                pane.getChildren().get(i).setDisable(false);
                pane.getChildren().get(i).setStyle("-fx-background-radius: 5%; -fx-border-radius: 5%");
                pane.getChildren().get(i).setEffect(null);
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