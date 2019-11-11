package minesweeper;

import minesweeper.domain.UserGame;
import minesweeper.domain.UserGameRepository;
import minesweeper.dto.Difficulty;
import minesweeper.dto.GameState;
import minesweeper.dto.MakeMoveRequest;
import minesweeper.gameLogic.Map;
import minesweeper.gameLogic.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*@... – annotations mostly (probably?) for Spring*/
@RestController
public class GameController {

    @Autowired
    UserGameRepository gameRepository;
    MapConverter mapConverter = new MapConverter();

    // generate new map for given columns and rows. Then return initial game state for player
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/map", method = RequestMethod.GET)
    public GameState create(@RequestParam(value = "difficulty_level", defaultValue = "Medium") Difficulty difficulty) throws Exception {
        int columns, rows, numberOfMines;
        switch (difficulty) {
            case Easy:
                columns = rows = 9;
                numberOfMines = 10;
                break;
            case Hard:
                columns = 16;
                rows = 30;
                numberOfMines = 99;
                break;
            case Medium:
                columns = rows = 16;
                numberOfMines = 40;
                break;
            default:
                throw new Exception("Invalid difficulty value");
        }
        Map map = new Map(columns, rows, numberOfMines);
        UserGame mapForDB = mapConverter.mapToUserGame(map, Stage.Continue, difficulty.toString());
        mapForDB = gameRepository.save(mapForDB);
        GameState gameState = mapConverter.mapToGameState(Stage.Continue, map, mapForDB.getId());

        return gameState;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/map", method = RequestMethod.POST)
    public GameState makeMove(@RequestBody MakeMoveRequest request) throws Exception {
        UserGame userGame = gameRepository.findById(request.getMapID()).get();
        Map map = mapConverter.userGameToMap(userGame);
        Stage stage = map.makeMove(request.getColumn(), request.getRow());
        UserGame userGameToDB = mapConverter.mapToUserGame(map, stage, userGame.getDifficulty());
        userGameToDB.setId(userGame.getId());
        gameRepository.save(userGameToDB);
        GameState gameState = mapConverter.mapToGameState(stage, map, request.getMapID());
        return gameState;
    }
}