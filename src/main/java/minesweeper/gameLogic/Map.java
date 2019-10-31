package minesweeper.gameLogic;

import java.util.Random;

import static minesweeper.gameLogic.Stage.*;

public class Map implements IMap {
    public Cell[][] cells;

    public Map() {
    }

    public Map(int columns, int rows) {
        cells = createRandomCells(columns, rows);
        populateNumbersBetweenMines(cells);
    }

    /*TODO: make different mine-rate for difficulty levels*/
    private static Cell[][] createRandomCells(int columns, int rows) {
        Random random = new Random();
        Cell[][] minesweeper_array = new Cell[columns][rows];
        for (int row = 0; row < rows; row++)
            for (int column = 0; column < columns; column++) {
                int randomDummy = random.nextInt(100);
                Cell cell = new Cell();
                cell.isAMine = randomDummy < 10;
                minesweeper_array[column][row] = cell;
            }
        return minesweeper_array;
    }

    private static void numberForCell(Cell[][] cells, int row, int column) {
        if (cells[column][row].isAMine) {
            return;
        }
        int result = 0;
        int rows = cells.length;
        int columns = cells[0].length;
        for (int c = column - 1; c < column + 2; c++)
            for (int r = row - 1; r < row + 2; r++) {
                if (c < 0 || c >= columns || r < 0 || r >= rows) continue;

                if (cells[c][r].isAMine) {
                    result++;
                }
            }
        cells[column][row].numberOfMines = result;
    }

    private static void populateNumbersBetweenMines(Cell[][] cells) {
        int rows = cells.length;
        int columns = cells[0].length;
        for (int row = 0; row < rows; row++)
            for (int column = 0; column < columns; column++) {
                numberForCell(cells, row, column);
            }
    }

    private void revealSurroundings(int column, int row) {
        int rows = cells.length;
        int columns = cells[0].length;
        if (!cells[column][row].isVisible) {
            cells[column][row].isVisible = true;
            if (cells[column][row].numberOfMines == 0) {
                for (int c = column - 1; c < column + 2; c++)
                    for (int r = row - 1; r < row + 2; r++) {
                        if (c < 0 || c >= columns || r < 0 || r >= rows) continue;
                        revealSurroundings(c, r);
                    }
            }
        }
    }

    /**
     * TODO @Override public void flagMove(int column, int row){
     * map[column][row].isFlagged=="X"
     * }
     */
    @Override
    public Stage makeMove(int column, int row) {
        if (cells[column][row].isAMine) {
            revealEveryCell();
            return Loss;
        } else {
            revealSurroundings(column, row);
            if (isGameOver()) {
                revealEveryCell();
                return Victory;
            } else {
                return Continue;
            }
        }
    }

    private boolean isGameOver() {
        for (int c = 0; c < cells.length; c++)
            for (int r = 0; r < cells[0].length; r++) {
                if (!cells[c][r].isVisible && !cells[c][r].isAMine) {
                    return false;
                }
            }
        return true;
    }

    private void revealEveryCell() {
        for (int c = 0; c < cells.length; c++)
            for (int r = 0; r < cells[0].length; r++) {
                cells[c][r].setIsVisible(true);
            }
    }

}