package ch.lukasakermann.connectfourchallenge.connectFourService.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class Board {

    private static final int TOP_ROW = 0;

    private final List<List<Cell>> board;

    @JsonCreator
    public Board(List<List<Cell>> board) {
        this.board = board;
    }

    public List<List<Cell>> getBoard() {
        return board;
    }

    public boolean isColumnFree(int i) {
        return Cell.EMPTY.equals(getTopRow().get(i));
    }

    private List<Cell> getTopRow() {
        return board.get(TOP_ROW);
    }


}
