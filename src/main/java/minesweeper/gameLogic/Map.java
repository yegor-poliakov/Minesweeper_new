package minesweeper.gameLogic;

import java.util.Random;

import static minesweeper.gameLogic.Stage.*;

public class Map implements IMap {
    public Cell[][] cells;

    public Map(int columns, int rows) {
        cells = createMap(columns, rows);
        numbersBetweenMines();
    }

    private Cell[][] createMap(int columns, int rows) {
        Random random = new Random();
        Cell[][] minesweeper_array = new Cell[columns][rows];
        for (int row = 0; row < rows; row++)
            for (int column = 0; column < columns; column++) {
                int a1 = random.nextInt(100);
                Cell cell = new Cell();
                cell.isAMine = a1 < 5;
                minesweeper_array[column][row] = cell;
            }
        return minesweeper_array;
    }

    private void numberForCell(int row, int column) {
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

    private void numbersBetweenMines() {

        int rows = cells.length;
        int columns = cells[0].length;

        for (int row = 0; row < rows; row++)
            for (int column = 0; column < columns; column++) {
                numberForCell(row, column);
            }
    }

    private void print(boolean forPlayer) {
        int rows = cells.length;
        int columns = cells[0].length;

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                Cell cell = cells[column][row];
                if (!forPlayer) {
                    cell.isVisible = true;
                }
                String toPrint = cell.cellToString();
                System.out.print(toPrint);
            }
            System.out.println();
        }
        System.out.println("");
    }

    @Override
    public void print() {
        print(false);
    }

    @Override
    public void printForPlayer() {
        print(true);
    }

    public void revealSurroundings(int column, int row) {
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

    @Override
    public Stage makeMove(int column, int row) {
        if (cells[column][row].isAMine) {
            for (int c = 0; c < cells.length; c++)
                for (int r = 0; r < cells[0].length; r++) {
                    Cell cell = cells[c][r];
                    if (cell.isAMine()) {
                        cell.setIsVisible(true);
                    }
                }
            return Loss;
        } else {
            revealSurroundings(column, row);
            for (int c = 0; c < cells.length; c++)
                for (int r = 0; r < cells[0].length; r++) {
                    if (!cells[c][r].isVisible && !cells[c][r].isAMine) {
                        return Continue;
                    }
                }
            for (int c = 0; c < cells.length; c++)
                for (int r = 0; r < cells[0].length; r++) {
                    cells[c][r].setIsVisible(true);
                }
            return Victory;
            }
    }
}
/**
 * @Override public void flagMove(int column, int row){
 * map[column][row].isFlagged=="X"
 * }
 */