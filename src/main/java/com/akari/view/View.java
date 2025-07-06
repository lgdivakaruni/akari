package com.akari.view;

import com.akari.controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class View implements FXComponent {
    private PuzzleView puzzleView;
    private LowerView lowerView;
    private UpperView upperView;
    private Scene scene;
    private Controller controller;

    public View(Controller controller) {
        this.controller = controller;
        this.puzzleView = new PuzzleView(controller);
        this.upperView = new UpperView(controller);
        this.lowerView = new LowerView(controller);
        this.scene = new Scene(render());
    }

    @Override
    public Parent render() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("border_pane");
        root.setTop(upperView.createHeader());
        root.setCenter(puzzleView.createGrid());
        root.setBottom(lowerView.createControlPanel());
        return root;
    }
}
