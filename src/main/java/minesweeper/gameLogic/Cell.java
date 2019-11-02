package minesweeper.gameLogic;

public class Cell {
    boolean isAMine;
    int numberOfMines;
    boolean isVisible;
    boolean isFlagged;

    public boolean isAMine() {
        return isAMine;
    }

    public void setIsAMine(boolean isMineInput) { isAMine = isMineInput; }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisibleInput) {
        isVisible = isVisibleInput;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setIsFlagged(boolean isFlaggedInput) {
        isFlagged = isFlaggedInput;
    }
}