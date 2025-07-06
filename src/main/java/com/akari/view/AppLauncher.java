package com.akari.view;

import com.akari.SamplePuzzles;
import com.akari.controller.Controller;
import com.akari.controller.ControllerImpl;
import com.akari.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AppLauncher extends Application {
    @Override
    public void start(Stage stage) {
        Puzzle puzzle1 = new PuzzleImpl(SamplePuzzles.PUZZLE_01);
        Puzzle puzzle2 = new PuzzleImpl(SamplePuzzles.PUZZLE_02);
        Puzzle puzzle3 = new PuzzleImpl(SamplePuzzles.PUZZLE_03);
        Puzzle puzzle4 = new PuzzleImpl(SamplePuzzles.PUZZLE_04);
        Puzzle puzzle5 = new PuzzleImpl(SamplePuzzles.PUZZLE_05);
        Puzzle puzzle6 = new PuzzleImpl(SamplePuzzles.PUZZLE_06);
        List<Puzzle> puzzles = new ArrayList<>();
        puzzles.add(puzzle1);
        puzzles.add(puzzle2);
        puzzles.add(puzzle3);
        puzzles.add(puzzle4);
        puzzles.add(puzzle5);
        puzzles.add(puzzle6);
        PuzzleLibrary library = new PuzzleLibraryImpl();
        library.addPuzzle(puzzle1);
        library.addPuzzle(puzzle2);
        library.addPuzzle(puzzle3);
        library.addPuzzle(puzzle4);
        library.addPuzzle(puzzle5);
        library.addPuzzle(puzzle6);
        Model model = new ModelImpl(library);
        model.setActivePuzzleIndex(4);
        Controller controller = new ControllerImpl(model);

        View root = new View(controller);
        Scene scene = new Scene(root.render(), 800, 800);
        scene.getStylesheets().add("main.css");
        model.addObserver(
                (Model m) -> {
                    scene.setRoot(root.render());
                    stage.sizeToScene();
                });
        stage.setScene(scene);
        stage.setTitle("AKARI");
        stage.show();
    }
}
