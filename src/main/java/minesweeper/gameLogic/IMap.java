package minesweeper.gameLogic;

public interface IMap {
    void print();

    void printForPlayer();

    Stage makeMove(int column, int row);
}