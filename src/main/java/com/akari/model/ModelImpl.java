package com.akari.model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
public class ModelImpl implements Model {
    private PuzzleLibrary library;
    private Puzzle activePuzzle; // Current active puzzles
    private int activePuzzleIndex; // Current active puzzle index
    private int rowNum; // Number of rows
    private int columnNum; // Number of columns
    private ArrayList<ModelObserver> observers;
    private boolean[][] lamps;
    private boolean[][] holdsClues;

    public ModelImpl(PuzzleLibrary library) {
        if (library == null) {
            throw new IllegalArgumentException("Invalid library");
        }
        this.library = library;
        this.observers = new ArrayList<>();
        this.activePuzzleIndex = 0;
        this.activePuzzle = getActivePuzzle();
        this.rowNum = activePuzzle.getWidth();
        this.columnNum = activePuzzle.getHeight();
        this.lamps = new boolean[rowNum][columnNum];
        this.holdsClues = new boolean[rowNum][columnNum];
        instantiateLamps();
        instantiateHoldsClues();
    }

  /*
  @Override
  public int getRowNum() {
      return activePuzzle.getHeight();
  }

  @Override
  public int getColumnNum() {
      return activePuzzle.getWidth();
  }

   */

    @Override
    public void addLamp(int r, int c) {
        // Bounds check
        if (!boundsCheck(r, c)) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
        // Corridor Cell Type check
        if (!activePuzzle.getCellType(r, c).equals(CellType.CORRIDOR)) {
            throw new IllegalArgumentException("Not a corridor cell");
        }
        // Lamp Check
        if (isLamp(r, c)) {
            throw new IllegalArgumentException("Lamp already exists.");
        }

        lamps[r][c] = true;

        notifyObservers();
    }

    @Override
    public void removeLamp(int r, int c) {
        // Bounds check
        if (!boundsCheck(r, c)) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
        // Corridor Cell Type check
        if (!activePuzzle.getCellType(r, c).equals(CellType.CORRIDOR)) {
            throw new IllegalArgumentException("Not a corridor cell");
        }

        if (isLamp(r, c)) {
            lamps[r][c] = false;
        }

        notifyObservers();
    }

    @Override
    public boolean isLit(int r, int c) {
        if (!boundsCheck(r, c)) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
        if (!activePuzzle.getCellType(r, c).equals(CellType.CORRIDOR)) {
            throw new IllegalArgumentException("Not a corridor cell.");
        }

        if (isLamp(r, c)) {
            return true;
        }

        // Check for lamps UP the row
        int rUP = r - 1;
        while (boundsCheck(rUP, c) && activePuzzle.getCellType(rUP, c).equals(CellType.CORRIDOR)) {
            if (isLamp(rUP, c)) {
                return true;
            }
            rUP--;
        }

        // Check for lamps DOWN the row
        int rDOWN = r + 1;
        while (boundsCheck(rDOWN, c) && activePuzzle.getCellType(rDOWN, c).equals(CellType.CORRIDOR)) {
            if (isLamp(rDOWN, c)) {
                return true;
            }
            rDOWN++;
        }

        // Check for lamps to the LEFT
        int cLEFT = c - 1;
        while (boundsCheck(r, cLEFT) && activePuzzle.getCellType(r, cLEFT).equals(CellType.CORRIDOR)) {
            if (isLamp(r, cLEFT)) {
                return true;
            }
            cLEFT--;
        }

        // Check for lamps to the RIGHT
        int cRIGHT = c + 1;
        while (boundsCheck(r, cRIGHT)
                && activePuzzle.getCellType(r, cRIGHT).equals(CellType.CORRIDOR)) {
            if (isLamp(r, cRIGHT)) {
                return true;
            }
            cRIGHT++;
        }
        return false;
    }

    @Override
    public boolean isLamp(int r, int c) {
        // Bounds check
        if (!boundsCheck(r, c)) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }

        if (!activePuzzle.getCellType(r, c).equals(CellType.CORRIDOR)) {
            throw new IllegalArgumentException("Not a corridor cell.");
        }

        return lamps[r][c];
    }

    @Override
    public boolean isLampIllegal(int r, int c) {
        boolean conflictingLamp = false;
        if (!boundsCheck(r, c)) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        if (!isLamp(r, c)) {
            throw new IllegalArgumentException("Lamp does not exist");
        }

        if (!activePuzzle.getCellType(r, c).equals(CellType.CORRIDOR)) {
            throw new IllegalArgumentException("Not a corridor cell");
        }

        // Check UP the row
        int rUP = r - 1;
        while (boundsCheck(rUP, c) && activePuzzle.getCellType(rUP, c).equals(CellType.CORRIDOR)) {
            if (isLamp(rUP, c)) {
                return true;
            }
            rUP--;
        }

        // Check DOWN the row
        int rDOWN = r + 1;
        while (boundsCheck(rDOWN, c) && activePuzzle.getCellType(rDOWN, c).equals(CellType.CORRIDOR)) {
            if (isLamp(rDOWN, c)) {
                return true;
            }
            rDOWN++;
        }

        // Check to the LEFT
        int cLEFT = c - 1;
        while (boundsCheck(r, cLEFT) && activePuzzle.getCellType(r, cLEFT).equals(CellType.CORRIDOR)) {
            if (isLamp(r, cLEFT)) {
                return true;
            }
            cLEFT--;
        }

        // Check to the RIGHT
        int cRIGHT = c + 1;
        while (boundsCheck(r, cRIGHT)
                && activePuzzle.getCellType(r, cRIGHT).equals(CellType.CORRIDOR)) {
            if (isLamp(r, cRIGHT)) {
                return true;
            }
            cRIGHT++;
        }
        return false;
    }

    @Override
    public Puzzle getActivePuzzle() {
        return library.getPuzzle(activePuzzleIndex);
    }

    @Override
    public int getActivePuzzleIndex() {
        return this.activePuzzleIndex;
    }

    @Override
    public void setActivePuzzleIndex(int index) {
        if (!(index < getPuzzleLibrarySize())) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        this.activePuzzleIndex = index;
        newPuzzle();
        notifyObservers();
    }

    /** HELPER METHOD sets the activePuzzle sets row and column num resets arrays */
    private void newPuzzle() {
        this.activePuzzle = library.getPuzzle(activePuzzleIndex);
        this.rowNum = activePuzzle.getHeight();
        this.columnNum = activePuzzle.getWidth();

        this.lamps = new boolean[rowNum][columnNum];
        this.holdsClues = new boolean[rowNum][columnNum];

        instantiateLamps();
        instantiateHoldsClues();
    }

    @Override
    public int getPuzzleLibrarySize() {
        return library.size();
    }

    @Override
    public void resetPuzzle() {
        instantiateLamps();
        notifyObservers();
    }

    @Override
    public boolean isSolved() {
        // Check clues are satisfied: loop through each cell
        for (int r = 0; r < rowNum; r++) {
            for (int c = 0; c < columnNum; c++) {
                if (activePuzzle.getCellType(r, c).equals(CellType.CLUE)) { //
                    if (!isClueSatisfied(r, c)) {
                        return false;
                    }
                }
            }
        }

        // Check every corridor is lit
        for (int r = 0; r < rowNum; r++) {
            for (int c = 0; c < columnNum; c++) {
                if (activePuzzle.getCellType(r, c).equals(CellType.CORRIDOR)) {
                    if (!isLit(r, c)) {
                        return false;
                    } else if (isLamp(r, c) && isLampIllegal(r, c)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean isClueSatisfied(int r, int c) {
        if (!boundsCheck(r, c)) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (!activePuzzle.getCellType(r, c).equals(CellType.CLUE)) {
            throw new IllegalArgumentException("Not a clue!");
        }

        int count = 0;

        if (boundsCheck(r + 1, c) && lamps[r + 1][c]) {
            count++;
        }

        if (boundsCheck(r - 1, c) && lamps[r - 1][c]) {
            count++;
        }

        if (boundsCheck(r, c + 1) && lamps[r][c + 1]) {
            count++;
        }

        if (boundsCheck(r, c - 1) && lamps[r][c - 1]) {
            count++;
        }

        return (count == activePuzzle.getClue(r, c));
    }

    @Override
    public void addObserver(ModelObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(ModelObserver observer) {
        observers.remove(observer);
    }

    /** HELPER METHODS */
    private boolean boundsCheck(int r, int c) {
        return (r < rowNum && r >= 0 && c < columnNum && c >= 0);
    }

    private void instantiateLamps() {
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < columnNum; j++) {
                lamps[i][j] = false;
            }
        }
    }

    private void instantiateHoldsClues() {
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < columnNum; j++) {
                if (activePuzzle.getCellType(i, j).equals(CellType.CLUE)) {
                    holdsClues[i][j] = true;
                }
            }
        }
    }

    private void notifyObservers() {
        for (ModelObserver observer : observers) {
            observer.update(this);
        }
    }

    private boolean clueViolation(int r, int c) {
        boolean check = false;
        if (!boundsCheck(r, c)) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        if (boundsCheck(r + 1, c) && activePuzzle.getCellType(r + 1, c).equals(CellType.CLUE)) {
            if (isClueSatisfied(r + 1, c)) {
                check = true;
            }
        }
        if (boundsCheck(r - 1, c) && activePuzzle.getCellType(r - 1, c).equals(CellType.CLUE)) {
            if (isClueSatisfied(r - 1, c)) {
                check = true;
            }
        }
        if (boundsCheck(r, c - 1) && activePuzzle.getCellType(r, c - 1).equals(CellType.CLUE)) {
            if (isClueSatisfied(r, c - 1)) {
                check = true;
            }
        }
        if (boundsCheck(r, c + 1) && activePuzzle.getCellType(r, c + 1).equals(CellType.CLUE)) {
            if (isClueSatisfied(r, c + 1)) {
                check = true;
            }
        }
        return check;
    }
}