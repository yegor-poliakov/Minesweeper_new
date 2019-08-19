package minesweeper;

public class CellState {
    private final CellType cellType;
    private final int numberOfMines;

    public CellState(CellType cellType, int numberOfMines) {
        this.cellType = cellType;
        this.numberOfMines = numberOfMines;
    }

    public CellType getCellType() {
        return cellType;
    }
    public int getNumberOfMines() {
        return numberOfMines;
    }

}