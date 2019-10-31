package minesweeper.dto;

public class GameState {
    private final GameStage stage;
    private final CellState[][] cells;

    public GameState(GameStage stage, CellState[][] cells) {
        this.stage = stage;
        this.cells = cells;
    }

    public GameStage getStage() {
        return stage;
    }

    public CellState[][] getCells() {
        return cells;
    }
}