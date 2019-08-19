package minesweeper.gameLogic;

public class Cell {
    boolean isAMine;
    int numberOfMines;
    boolean isVisible;

    // boolean isFlagged;
    public String cellToString() {
        if (isVisible) {
            if (isAMine /** && isFlagged*/) {
                return "X";
            } else {
                return Integer.toString(numberOfMines);
            }
        } else {
            return "?";
        }
    }

    public boolean isAMine() {
        return isAMine;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public boolean isVisible() {
        return isVisible;
    }
    public void setIsVisible(boolean isVisibleInput){isVisible = isVisibleInput;}
}