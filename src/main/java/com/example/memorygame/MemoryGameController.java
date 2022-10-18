package com.example.memorygame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.action.Action;

import java.util.ArrayList;

public class MemoryGameController {

    @FXML
    private Button playAgainButton;
    @FXML
    private AnchorPane pane;
    @FXML
    private Label numberOfAttempts;

    private String[] colors = {"#fce303","#fce303", "#338f35", "#338f35", "#4287f5", "#4287f5", "#f54242", "#f54242", "#f57542", "#f57542",
                                "#f5c440", "#f5c440", "#a3f540", "#a3f540", "#40f5dd", "#40f5dd", "#f549de", "#f549de", "#5e0f28", "#5e0f28"};
    private String[] randomColors = new String[colors.length];

    private boolean gameStarted = false;
    private boolean openedCard = false;
    private Button openedButton, previousButton;

    private int clickCount = 0;
    private int numberOfMatches = 0;
    private int attempts = 0;

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
            openedButton.setDisable(false);
            openedButton.setStyle("-fx-background-radius: 5%; -fx-border-radius: 5%");
            previousButton.setDisable(false);
            previousButton.setStyle("-fx-background-radius: 5%; -fx-border-radius: 5%");
            return;
        }

        int colorIndex = Integer.parseInt(btn.getId());

        if (openedCard) {
            btn.setDisable(true);
            btn.setStyle("-fx-background-color: " + randomColors[colorIndex] + ";-fx-background-radius: 5%; -fx-border-radius: 5%");
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
            btn.setDisable(true);
            btn.setStyle("-fx-background-color: " + randomColors[colorIndex] + ";-fx-background-radius: 5%; -fx-border-radius: 5%");
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
            }
        }
        gameStarted = false;
        playAgainButton.setOpacity(0);
        numberOfMatches = 0;
        attempts = 0;
        numberOfAttempts.setText("Number of Attempts: " + attempts);
    }
}