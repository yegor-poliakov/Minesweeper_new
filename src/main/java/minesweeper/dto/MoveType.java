package minesweeper.dto;

public enum MoveType {
    OpenCell,
    PutFlag,
    // CheckFlags means to open cell taking flags into account
    CheckFlags
}
