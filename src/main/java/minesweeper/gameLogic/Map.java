package minesweeper.gameLogic;

import minesweeper.dto.Difficulty;

import java.util.ArrayList;
import java.util.Collections;

import static minesweeper.gameLogic.Stage.*;

public class Map implements IMap {
    public Cell[][] cells;

    public Map() {
    }

    public Map(int columns, int rows, int numberOfMines) {
        cells = createRandomCells(columns, rows, numberOfMines);
        populateNumbersBetweenMines(cells);
    }

    private static class Coordinate{
        int x;
        int y;
        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static Coordinate[] getMineCoordinates(int columns, int rows, int numberOfMines) {
        ArrayList<Coordinate> forShuffle = new ArrayList<>();
        for (int row = 0; row < rows; row++)
            for (int column = 0; column < columns; column++) {
                Coordinate coord = new Coordinate(column, row);
                forShuffle.add(coord);
            }
        Collections.shuffle(forShuffle);
        Coordinate[] resultMines = new Coordinate[numberOfMines];
        for(int i = 0; i < numberOfMines; i++) {
            resultMines[i] = forShuffle.get(i);
        }
        return resultMines;
    }

    private static Cell[][] createRandomCells(int columns, int rows, int numberOfMines) {
        Cell[][] cells = new Cell[rows][columns];
        Coordinate[] mines = getMineCoordinates(columns, rows, numberOfMines);
        for (int row = 0; row < rows; row++)
            for (int column = 0; column < columns; column++) {
                Cell cell = new Cell();
                cells[row][column] = cell;
            }
        for (Coordinate m : mines){
            cells[m.y][m.x].setAMine(true);
        }
        return cells;
    }

    private static void numberForCell(Cell[][] cells, int row, int column) {
        if (cells[row][column].isAMine()){
            return;
        }
        int result = 0;
        int rows = cells.length;
        int columns = cells[0].length;
        for (int c = column - 1; c < column + 2; c++)
            for (int r = row - 1; r < row + 2; r++) {
                if (c < 0 || c >= columns || r < 0 || r >= rows) continue;

                if (cells[r][c].isAMine()) {
                    result++;
                }
            }
        cells[row][column].setNumberOfMines(result);
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
        if (!cells[column][row].isVisible()) {
            cells[column][row].setVisible(true);
            if (cells[column][row].getNumberOfMines() == 0) {
                for (int c = column - 1; c < column + 2; c++)
                    for (int r = row - 1; r < row + 2; r++) {
                        if (c < 0 || c >= columns || r < 0 || r >= rows) continue;
                        revealSurroundings(c, r);
                    }
            }
        }
    }

    private void revealSurroundingsFlagDependent(int column, int row) {
        int rows = cells.length;
        int columns = cells[0].length;
        for (int c = column - 1; c < column + 2; c++)
            for (int r = row - 1; r < row + 2; r++) {
                if (cells[columns][rows].isFlagged()) continue;
                revealSurroundings(c, r);
            }
    }

    private int countFlagsAround(int column, int row) {
        int counter = 0;
        for (int c = column - 1; c < column + 2; c++)
            for (int r = row - 1; r < row + 2; r++) {
                if (cells[c][r].isFlagged()) {
                    counter++;
                }
            }
        return counter;
    }

    private boolean needToRevealWithFlags(int column, int row) {
        Cell cell = cells[column][row];
        if(cell.isVisible()){
            int flags = countFlagsAround(column, row);
            return flags == cell.getNumberOfMines();
        }
        return false;
    }

    public Stage checkFlagMove(int column, int row) {
        if(needToRevealWithFlags(column, row)) {
            for (int c = column - 1; c < column + 2; c++)
                for (int r = row - 1; r < row + 2; r++) {
                    Cell cell = cells[c][r];
                    if(cell.isFlagged()){
                        continue;
                    } else if (cell.isAMine()){
                        revealEveryCell();
                        return Loss;
                    } else {
                        revealSurroundings(c, r);
                    }
                }
            return checkEndGame();
        } else {
            return Continue;
        }
    }

    public void flagMove(int column, int row) {
        if (!cells[column][row].isVisible()){
            cells[column][row].setFlagged(true);
        }
    }

    @Override
    public Stage makeMove(int column, int row) {
        if (cells[column][row].isAMine()) {
            revealEveryCell();
            return Loss;
        } else {
            revealSurroundings(column, row);
            return checkEndGame();
        }
    }

    public Stage checkEndGame() {
        if (isGameOver()) {
            revealEveryCell();
            return Victory;
        } else {
            return Continue;
        }
    }


    private boolean isGameOver() {
        for (int c = 0; c < cells.length; c++)
            for (int r = 0; r < cells[0].length; r++) {
                if (!cells[c][r].isVisible() && !cells[c][r].isAMine()) {
                    return false;
                }
            }
        return true;
    }

    private void revealEveryCell() {
        for (int c = 0; c < cells.length; c++)
            for (int r = 0; r < cells[0].length; r++) {
                cells[c][r].setVisible(true);
            }
    }
}