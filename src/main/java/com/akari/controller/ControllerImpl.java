package com.akari.controller;

import com.akari.model.Model;
import com.akari.model.Puzzle;
import java.util.Random;
public class ControllerImpl implements Controller {
    private Model model;

    public ControllerImpl(Model model) {
        this.model = model;
    }

    @Override
    public void clickNextPuzzle() {
        int current_index = model.getActivePuzzleIndex();
        int next_index = current_index + 1;
        if (next_index < model.getPuzzleLibrarySize() - 1) {
            model.setActivePuzzleIndex(next_index);
        } else {
            model.setActivePuzzleIndex(0);
        }
    }

    @Override
    public void clickPrevPuzzle() {
        int current_index = model.getActivePuzzleIndex();
        int previous_index = current_index - 1;
        if (previous_index >= 0) {
            model.setActivePuzzleIndex(previous_index);
        } else {
            model.setActivePuzzleIndex(model.getPuzzleLibrarySize() - 1);
        }
    }

    @Override
    public void clickRandPuzzle() {

        // Case 1: the size of the puzzle library is one or less.
        if (model.getPuzzleLibrarySize() <= 1) {
            return; // do nothing
        }

        // Base Case: multiple puzzles in the library
        int current_index = model.getActivePuzzleIndex();
        Random random = new Random();
        int randomIndex = random.nextInt(model.getPuzzleLibrarySize());
        // Keep generating another random index
        while (randomIndex == current_index) {
            randomIndex = random.nextInt(model.getPuzzleLibrarySize());
        }
        // Set next active puzzle
        model.setActivePuzzleIndex(randomIndex);
    }

    @Override
    public void clickResetPuzzle() {
        model.resetPuzzle();
    }

    @Override
    public void addLamp(int r, int c) {
        model.addLamp(r, c);
    }

    @Override
    public void removeLamp(int r, int c) {
        model.removeLamp(r, c);
    }

    @Override
    public boolean isLit(int r, int c) {
        return model.isLit(r, c);
    }

    @Override
    public boolean isLamp(int r, int c) {
        return model.isLamp(r, c);
    }

    @Override
    public boolean isClueSatisfied(int r, int c) {
        return model.isClueSatisfied(r, c);
    }

    @Override
    public boolean isSolved() {
        return model.isSolved();
    }

    @Override
    public Puzzle getActivePuzzle() {
        return model.getActivePuzzle();
    }

    @Override
    public boolean isLampIllegal(int r, int c) {
        return model.isLampIllegal(r, c);
    }

    @Override
    public int getActivePuzzleIndex() {
        return model.getActivePuzzleIndex();
    }
}
