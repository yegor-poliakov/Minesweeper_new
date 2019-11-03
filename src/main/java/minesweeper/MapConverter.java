package minesweeper;

import com.fasterxml.jackson.databind.ObjectMapper;
import minesweeper.domain.*;
import minesweeper.dto.*;
import minesweeper.gameLogic.*;

import java.io.IOException;

public class MapConverter {

    GameState mapToGameState(Stage stage, Map map, long mapID) throws Exception {
        CellState[][] cellStates = cellsToCellStates(map.cells);
        GameStage gameStage = stageToGameStage(stage);

        return new GameState(gameStage, cellStates, mapID);
    }

    public UserGame mapToUserGame(Map map, Stage stage, String difficulty) {
        UserGame userGame = new UserGame();
        userGame.setStage(stage + "");
        userGame.setDifficulty(difficulty);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String jsonMap = objectMapper.writeValueAsString(map.cells);
            userGame.setCells(jsonMap);
        } catch (Exception e) {
            return null;
        }

        return userGame;
    }

    public Map userGameToMap(UserGame userGame) throws IOException {
        Map map = new Map();
        ObjectMapper objectMapper = new ObjectMapper();
        map.cells = objectMapper.readValue(userGame.getCells(), Cell[][].class);
        return map;
    }

    private CellState[][] cellsToCellStates(Cell[][] cells) {
        int columns = cells.length;
        int rows = cells[0].length;

        CellState[][] cellStates = new CellState[columns][rows];
        for (int i = 0; i < columns; i++)
            for (int j = 0; j < rows; j++) {
                cellStates[i][j] = cellToCellState(cells[i][j]);
            }

        return cellStates;
    }

    private CellState cellToCellState(Cell cell) {
        if (!cell.isVisible()) {
            return new CellState(CellType.Invisible, 0);
        } else if (cell.isAMine()) {
            return new CellState(CellType.Mine, 0);
        } else if (cell.isFlagged()) {
            return new CellState(CellType.Flag, cell.getNumberOfMines());
        } else {
            return new CellState(CellType.Number, cell.getNumberOfMines());
        }

    }

    private GameStage stageToGameStage(Stage stage) throws Exception {
        switch (stage) {
            case Continue:
                return GameStage.Continue;
            case Victory:
                return GameStage.Victory;
            case Loss:
                return GameStage.Loss;
            default:
                throw new Exception("Someone has messed with the code! Check GameStage ENUM");
        }
    }
}