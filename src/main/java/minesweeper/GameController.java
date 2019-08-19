// TODO fix game logic (reveal mines when lost), alert when u've lost (?).
// dto,
package minesweeper;

import minesweeper.gameLogic.Cell;
import minesweeper.gameLogic.Map;
import minesweeper.gameLogic.Stage;
import org.springframework.web.bind.annotation.*;

import static minesweeper.CellType.Invisible;
import static minesweeper.CellType.Mine;

@RestController
public class GameController {

    static enum Difficulty {
        Easy,
        Hard,
        Medium
    }

    Map map = null;

    // TODO generate new map for given columns and rows. Then return initial game state for player
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/map", method = RequestMethod.GET)
    public GameState create(@RequestParam(value = "difficulty_level", defaultValue = "Medium") Difficulty difficulty) {
        int columns, rows;
        switch (difficulty) {
            case Easy:
                columns = rows = 10;
                break;
            case Hard:
                columns = rows = 20;
                break;
            case Medium:
            default:
                columns = rows = 15;
                break;
        }
        map = new Map(columns, rows);

        GameState gameState = mapToGameState(Stage.Continue, map);

        return gameState;
    }

    private GameState mapToGameState(Stage stage, Map map) {
        // what map.map does?
        Cell[][] cells = map.cells;
        int columns = cells.length;
        int rows = cells[0].length;

        CellState[][] cellStates = new CellState[columns][rows];
        for (int i = 0; i < columns; i++)
            for (int j = 0; j < rows; j++) {
                Cell cellToUpdate = cells[i][j];
                if (!cellToUpdate.isVisible()) {
                    cellStates[i][j] = new CellState(CellType.Invisible, 0);
                } else if (cellToUpdate.isAMine()) {
                    cellStates[i][j] = new CellState(CellType.Mine, 0);
                } else {
                    cellStates[i][j] = new CellState(CellType.Number, cellToUpdate.getNumberOfMines());
                }
            }

        // TODO for each column and row set cellStates value to equivalent of "cells" value at the same coordinate

        GameStage gameStage;
            switch(stage){
                case Continue:
                    gameStage = GameStage.Continue;
                    break;
                case Victory:
                    gameStage = GameStage.Victory;
                    break;
                case Loss:
                default:
                    gameStage = GameStage.Loss;
                    break;
            }

        GameState state = new GameState(gameStage, cellStates);
        return state;
    }

    // TODO make a move for given coordinates and action. Then return an object with game state
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/map", method = RequestMethod.POST)
    public GameState makeMove(@RequestParam(value = "column") int column, @RequestParam(value = "row") int row) throws Exception {
        if (map == null) {
            throw new Exception("No map created yet");
        }
        Stage stage = map.makeMove(column, row);

        GameState gameState = mapToGameState(stage, map);

        return gameState;
    }
}