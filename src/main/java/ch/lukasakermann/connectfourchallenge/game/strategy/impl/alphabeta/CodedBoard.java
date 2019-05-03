package ch.lukasakermann.connectfourchallenge.game.strategy.impl.alphabeta;

import ch.lukasakermann.connectfourchallenge.connectFourService.dto.Board;
import ch.lukasakermann.connectfourchallenge.connectFourService.dto.Cell;

import java.util.Collection;
import java.util.stream.Collectors;

public class CodedBoard {

    static final char CODE_EMPTY = 'E';
    static final char CODE_RED = 'R';
    static final char CODE_YELLOW = 'Y';

    private static final int NUMBER_OF_COLUMNS = 7;
    private static final int NUMBER_OF_ROWS = 6;

    private final String boardCode;

    private CodedBoard(String boardCode) {
        this.boardCode = boardCode;
    }

    static CodedBoard from(Board board) {
        return new CodedBoard(
                encode(board)
        );
    }

    private static String encode(Board board) {
        return board.getBoard().stream()
                .flatMap(Collection::stream)
                .map(CodedBoard::toCode)
                .collect(Collectors.joining());
    }

    private static String toCode(Cell cell) {
        char code;
        switch (cell) {
            case EMPTY:
                code= CODE_EMPTY;
                break;
            case RED:
                code = CODE_RED;
                break;
            case YELLOW:
                code= CODE_YELLOW;
                break;
            default:
                throw new IllegalArgumentException("Unknown cell " + cell);
        }
        return String.valueOf(code);
    }

    int getNumberOfColumns() {
        return NUMBER_OF_COLUMNS;
    }

    int getNumberOfRows() {
        return NUMBER_OF_ROWS;
    }

    String getBoardCode() {
        return boardCode;
    }

    public CodedBoard dropDisc(int column, char color) {
        int row = getNumberOfRows() - 1;
        while (!isCellFree(row, column)) {
            row = row - 1;
        }

        return setCell(row, column, color);
    }

    private CodedBoard setCell(int row, int column, char color) {
        int replacementIndex = getIndex(row, column);
        String pre = boardCode.substring(0, replacementIndex);
        String post = boardCode.substring(replacementIndex+1);
        return new CodedBoard(pre + color + post);
    }

    boolean isColumnFree(int column) {
        return isCellFree(0, column);
    }

    private boolean isCellFree(int row, int column) {
        return CODE_EMPTY == getCell(row, column);

    }

    char getCell(int row, int column) {
        return getCell(getIndex(row, column));
    }

    private int getIndex(int row, int column) {
        return column + row * getNumberOfColumns();
    }

    private char getCell(int index) {
        return boardCode.charAt(index);
    }

    boolean isTerminal() {
        return isFull()
                || isWinning(CODE_RED)
                || isWinning(CODE_YELLOW);
    }

    boolean isFull() {
        final int row = getNumberOfRows() - 1;
        for (int column = 0; column < getNumberOfColumns(); column++) {
            if (isCellFree(row, column)) {
                return false;
            }
        }
        return true;
    }

    // https://stackoverflow.com/questions/32770321/connect-4-check-for-a-win-algorithm
    // https://stackoverflow.com/users/6381975/ferdelolmo
    boolean isWinning(char playerColor) {
        // horizontalCheck
        for (int row = 0; row < getNumberOfRows() - 3; row++) {
            for (int column = 0; column < getNumberOfColumns(); column++) {
                if (getCell(row, column) == playerColor
                        && getCell(row + 1, column) == playerColor
                        && getCell(row + 2, column) == playerColor
                        && getCell(row + 3, column) == playerColor) {
                    return true;
                }
            }
        }
        // verticalCheck
        for (int column = 0; column < getNumberOfColumns() - 3; column++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                if (getCell(row, column) == playerColor
                        && getCell(row, column + 1) == playerColor
                        && getCell(row, column + 2) == playerColor
                        && getCell(row, column + 3) == playerColor) {
                    return true;
                }
            }
        }
        // ascendingDiagonalCheck
        for (int column = 3; column < getNumberOfColumns(); column++) {
            for (int row = 0; row < getNumberOfRows() - 3; row++) {
                if (getCell(row, column) == playerColor
                        && getCell(row+1, column - 1) == playerColor
                        && getCell(row+2, column - 2) == playerColor
                        && getCell(row+3, column - 3) == playerColor)
                    return true;
            }
        }
        // descendingDiagonalCheck
        for (int column = 3; column < getNumberOfColumns(); column++) {
            for (int row = 3; row < getNumberOfRows(); row++) {
                if (getCell(row, column) == playerColor
                        && getCell(row - 1, column - 1) == playerColor
                        && getCell(row - 2, column - 2) == playerColor
                        && getCell(row - 3, column - 3) == playerColor)
                    return true;
            }
        }
        return false;
    }
}
