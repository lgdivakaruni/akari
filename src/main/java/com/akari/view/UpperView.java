package com.akari.view;

import com.akari.controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UpperView {
    private Controller controller;

    public UpperView(Controller controller) {
        this.controller = controller;
    }

    public VBox createHeader() {
        VBox upperPanel = new VBox();
        Label gameName = new Label("Akari");
        gameName.getStyleClass().add("title");
        gameName.getStyleClass().add("center-text");
        upperPanel.getChildren().add(gameName);

        Label gameNumber =
                new Label("Puzzle " + Integer.toString(this.controller.getActivePuzzleIndex()));
        gameNumber.getStyleClass().add("game-number");
        gameName.getStyleClass().add("center-text");
        upperPanel.getChildren().add(gameNumber);

        upperPanel.setAlignment(Pos.CENTER);
        return upperPanel;
    }
}
