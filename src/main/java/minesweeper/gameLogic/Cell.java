package minesweeper.gameLogic;

public class Cell {
    private boolean isAMine;
    private int numberOfMines;
    private boolean isVisible;
    private boolean isFlagged;

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isAMine() {
        return isAMine;
    }

    public void setAMine(boolean AMine) {
        isAMine = AMine;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public void setNumberOfMines(int numberOfMines) {
        this.numberOfMines = numberOfMines;
    }
}