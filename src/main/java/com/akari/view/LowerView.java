package com.akari.view;

import com.akari.controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LowerView {
    private Controller controller;
    private String message;
    private Label finalFeedback;

    public LowerView(Controller controller) {
        this.controller = controller;
        this.message = "Solve the puzzle!";
        this.finalFeedback = new Label(this.message);
    }

    public VBox createControlPanel() {
        VBox lowerPanel = new VBox();
        HBox controlPanel = new HBox(10);
        controlPanel.getStyleClass().add("controls");

        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> resetButtonHandler());
        resetButton.getStyleClass().add("controls-buttons");

        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> nextButtonHandler());
        nextButton.getStyleClass().add("controls-buttons");

        Button randomButton = new Button("Random");
        // randomButton.setOnAction(e -> controller.clickRandPuzzle());
        randomButton.setOnAction(e -> randomButtonHandler());
        randomButton.getStyleClass().add("controls-buttons");

        Button previousButton = new Button("Previous");
        previousButton.setOnAction(e -> previousButton());
        previousButton.getStyleClass().add("controls-buttons");

        controlPanel.getChildren().addAll(previousButton, nextButton, randomButton, resetButton);
        controlPanel.setAlignment(Pos.CENTER);

        HBox doneButton = new HBox(10);
        doneButton.setAlignment(Pos.BOTTOM_CENTER);
        doneButton.getStyleClass().add("done");

        Button button = new Button("Check Solution");
        button.setOnAction(e -> solveButton());
        button.getStyleClass().add("done-button");
        doneButton.getChildren().add(button);

        finalFeedback.getStyleClass().add("final-feedback");
        finalFeedback.getStyleClass().add("center-text");

        lowerPanel.getChildren().add(controlPanel);
        lowerPanel.getChildren().addAll(doneButton, finalFeedback);
        lowerPanel.setAlignment(Pos.CENTER);
        return lowerPanel;
    }

    public void solveButton() {
        if (controller.isSolved()) {
            finalFeedback.setText("Correct <3");
        } else {
            finalFeedback.setText("Incorrect :( ");
        }
    }

    public void nextButtonHandler() {
        controller.clickNextPuzzle();
        finalFeedback.setText(this.message);
    }

    public void randomButtonHandler() {
        controller.clickRandPuzzle();
        finalFeedback.setText(this.message);
    }

    public void resetButtonHandler() {
        controller.clickResetPuzzle();
        finalFeedback.setText(this.message);
    }

    public void previousButton() {
        controller.clickPrevPuzzle();
        finalFeedback.setText(this.message);
    }
}
