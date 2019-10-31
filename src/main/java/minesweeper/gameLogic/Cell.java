package minesweeper.gameLogic;

public class Cell {
    boolean isAMine;
    int numberOfMines;
    boolean isVisible;

    public boolean isAMine() {
        return isAMine;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisibleInput) {
        isVisible = isVisibleInput;
    }
}