package minesweeper.dto;

public class GameState {
    private final GameStage stage;
    private final CellState[][] cells;
    private final long mapID;

    public GameState(GameStage stage, CellState[][] cells, long mapID) {
        this.stage = stage;
        this.cells = cells;
        this.mapID = mapID;
    }

    public GameStage getStage() {
        return stage;
    }

    public CellState[][] getCells() {
        return cells;
    }

    public long getMapID() {
        return mapID;
    }
}