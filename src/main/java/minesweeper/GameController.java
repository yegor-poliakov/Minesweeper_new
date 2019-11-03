package minesweeper;

import minesweeper.domain.UserGameRepository;
import minesweeper.dto.Difficulty;
import minesweeper.dto.GameState;
import minesweeper.gameLogic.Map;
import minesweeper.gameLogic.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*@... – annotations mostly (probably?) for Spring*/
@RestController
public class GameController {

/*
    @Autowired
    UserGameRepository gameRepository;
*/
    GameStateConverter gameConverter = new GameStateConverter();
    Map map = null;

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
                columns = 30;
                rows = 16;
                numberOfMines = 99;
                break;
            case Medium:
                columns = rows = 16;
                numberOfMines = 40;
                break;
            default:
                throw new Exception("Invalid difficulty value");
        }
        map = new Map(columns, rows, numberOfMines);

        GameState gameState = gameConverter.mapToGameState(Stage.Continue, map);

        return gameState;
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/map", method = RequestMethod.POST)
    public GameState makeMove(@RequestParam(value = "column") int column, @RequestParam(value = "row") int row) throws Exception {
        if (map == null) {
            throw new Exception("No map created yet");
        }
        Stage stage = map.makeMove(column, row);

        GameState gameState = gameConverter.mapToGameState(stage, map);

        return gameState;
    }


/*    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/temp", method = RequestMethod.GET)
    public List<UserGame> temp() {
        UserGame userGame = new UserGame();
        userGame.setDifficulty("Medium");
        userGame.setStage("true");
        userGame.setCells("ABC");
        gameRepository.save(userGame);
        List<UserGame> games = gameRepository.findAll();
        return games;
    }*/
}