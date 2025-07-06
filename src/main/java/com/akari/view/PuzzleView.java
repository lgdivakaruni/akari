package com.akari.view;

import com.akari.controller.Controller;
import com.akari.model.CellType;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import java.awt.*;

public class PuzzleView {
    // ENCAPSULATION
    private final Controller controller;
    private Button[][] buttons;

    public PuzzleView(Controller controller) {
        this.controller = controller;
    }

    public GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setMaxHeight(300);
        grid.setMaxHeight(400);
        grid.setAlignment(Pos.CENTER);
        buttons =
                new Button[controller.getActivePuzzle().getHeight()]
                        [controller.getActivePuzzle().getWidth()];
        grid.getStyleClass().add("grid");
        // CREATE GRID
        int rowNum = controller.getActivePuzzle().getHeight();
        int columnNum = controller.getActivePuzzle().getWidth();
        for (int r = 0; r < rowNum; r++) {
            for (int c = 0; c < columnNum; c++) {
                Button button = new Button();
                int finalR = r;
                int finalC = c;
                int finalR1 = r;
                int finalC1 = c;
                button.setOnAction(e -> handleClickEvent(button, finalR1, finalC1));
                // button.setGraphic(null);
                button.setText("");
                formatButton(button, r, c);
                grid.add(button, c, r);
            }
        }
        return grid;
    }

    public void handleClickEvent(Button button, int r, int c) {
        // If the button has a lamp already
        if (controller.isLamp(r, c)) {
            // remove lamp
            controller.removeLamp(r, c);
            formatButton(button, r, c);
        }
        // If the button does not have a lamp
        else {
            if (controller
                    .getActivePuzzle()
                    .getCellType(r, c)
                    .equals(CellType.CORRIDOR)) { // AND the cell is a corridor cell
                controller.addLamp(r, c);

                formatButton(button, r, c);
            }
        }
    }

    public void formatButton(Button button, int r, int c) {
        // Case 1: Button is a CLUE
        if (controller.getActivePuzzle().getCellType(r, c).equals(CellType.CLUE)) {
            int clue = controller.getActivePuzzle().getClue(r, c);
            button.setText(String.valueOf(clue));
            // If the clue is NOT SATISFIED:
            if (!controller.isClueSatisfied(r, c)) {
                button.getStyleClass().add("clue-button");
                button.getStyleClass().add("grid-button-dimensions");
            }
            // If the clue is SATISFIED:
            else {
                button.getStyleClass().add("clue-satisfied-button");
                button.getStyleClass().add("grid-button-dimensions");
            }
        }
        // Case 2: Button is a WALL
        else if (controller.getActivePuzzle().getCellType(r, c).equals(CellType.WALL)) {
            button.getStyleClass().add("wall-button");
            button.getStyleClass().add("grid-button-dimensions");
        }
        // Case 3: Button is a CORRIDOR
        else if (controller.getActivePuzzle().getCellType(r, c).equals(CellType.CORRIDOR)) {
            // If the clue is a lamp and not illegal:
            if (controller.isLamp(r, c) && !controller.isLampIllegal(r, c)) {
                button.getStyleClass().add("lamp-button");
                button.getStyleClass().add("grid-button-dimensions");
                // button.setText("L");
                Image lamp = new Image("light-bulb.png");
                ImageView lampView = new ImageView(lamp);
                lampView.setFitWidth(10);
                lampView.setFitHeight(10);
                lampView.setImage(lamp);
                button.setGraphic(lampView);
            }
            // If the clue is a lamp and illegal:
            else if (controller.isLamp(r, c) && controller.isLampIllegal(r, c)) {
                button.getStyleClass().add("illegal-lamp-button");
                button.getStyleClass().add("grid-button-dimensions");
                // button.setText("L");
                Image lamp = new Image("light-bulb.png");
                ImageView lampView = new ImageView(lamp);
                lampView.setFitWidth(10);
                lampView.setFitHeight(10);
                lampView.setImage(lamp);
                button.setGraphic(lampView);
            }
            // If the clue is lit:
            else if (controller.isLit(r, c)) {
                button.getStyleClass().add("lit-button");
                button.getStyleClass().add("grid-button-dimensions");
            } else {
                button.getStyleClass().add("blank-button");
                button.getStyleClass().add("grid-button-dimensions");
            }
        }
    }
}
