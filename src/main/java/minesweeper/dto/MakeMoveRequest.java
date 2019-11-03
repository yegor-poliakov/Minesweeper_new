package minesweeper.dto;

public class MakeMoveRequest {
    private int column;
    private int row;
    private long mapID;
    private MoveType moveType;


    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public long getMapID() {
        return mapID;
    }

    public void setMapID(long mapID) {
        this.mapID = mapID;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }
}
