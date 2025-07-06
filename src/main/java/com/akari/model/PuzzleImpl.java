package com.akari.model;

public class PuzzleImpl implements Puzzle {
    private int width;
    private int height;
    private int[][] board;

    public PuzzleImpl(int[][] board) {
        this.board = board;
        this.width = board[0].length; // row width
        this.height = board.length; // column length
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public CellType getCellType(int r, int c) {
        if (!boundsCheck(r, c)) {
            throw new IndexOutOfBoundsException("Invalid row or column inputs.");
        }
        int cellValue = board[r][c];
        if (cellValue == 5) {
            return CellType.WALL;
        } else if (cellValue == 6) {
            return CellType.CORRIDOR;
        } else {
            return CellType.CLUE;
        }
    }

    @Override
    public int getClue(int r, int c) {
        if (!boundsCheck(r, c)) {
            throw new IndexOutOfBoundsException("Invalid row or column inputs.");
        }
        int cellValue = board[r][c];
        if (cellValue == 5 || cellValue == 6) {
            throw new IllegalArgumentException("Not a Clue Cell.");
        }
        return cellValue;
    }

    private boolean boundsCheck(int r, int c) {
        return (r < this.height) && (c < this.width) && (r >= 0) && (c >= 0);
    }
}
