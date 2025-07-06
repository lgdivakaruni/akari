package com.akari.controller;

import com.akari.model.Puzzle;

public interface Controller {
    /** Click action to go to the next puzzle */
    void clickNextPuzzle();

    /** Click action to go to the previous puzzle */
    void clickPrevPuzzle();

    /** Click action to go to a random puzzle */
    void clickRandPuzzle();

    /** Click action to reset the currently active puzzle */
    void clickResetPuzzle();

    void removeLamp(int r, int c);
    void addLamp(int r, int c);

    /** Returns true if the CORRIDOR cell at row r, column c is lit */
    boolean isLit(int r, int c);

    /** Returns true if the CORRIDOR cell at row r, column c is a lamp */
    boolean isLamp(int r, int c);

    /** Returns true if the CLUE cell at row r, column c is satisfied by nearby lamps */
    boolean isClueSatisfied(int r, int c);

    /** Returns true if the active puzzle is solved */
    boolean isSolved();

    /** Getter method for the active puzzle */
    Puzzle getActivePuzzle();

    boolean isLampIllegal(int r, int c);
    int getActivePuzzleIndex();
}


