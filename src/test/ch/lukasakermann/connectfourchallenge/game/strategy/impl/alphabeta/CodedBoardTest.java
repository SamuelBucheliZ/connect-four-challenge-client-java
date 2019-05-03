package ch.lukasakermann.connectfourchallenge.game.strategy.impl.alphabeta;


import ch.lukasakermann.connectfourchallenge.connectFourService.dto.Board;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static ch.lukasakermann.connectfourchallenge.connectFourService.dto.Cell.*;
import static ch.lukasakermann.connectfourchallenge.game.strategy.impl.alphabeta.CodedBoard.CODE_RED;
import static ch.lukasakermann.connectfourchallenge.game.strategy.impl.alphabeta.CodedBoard.CODE_YELLOW;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class CodedBoardTest {

    @Test
    void test_fromBoard() {
        Board board = new Board(Arrays.asList(
                Arrays.asList(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(EMPTY, EMPTY, RED, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(YELLOW, EMPTY, YELLOW, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(RED, EMPTY, YELLOW, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(RED, EMPTY, YELLOW, EMPTY, EMPTY, EMPTY, RED)
        ));

        CodedBoard codedBoard = CodedBoard.from(board);

        assertThat(codedBoard.getBoardCode(), is(
                "EEEEEEE" +
                        "EEEEEEE" +
                        "EEREEEE" +
                        "YEYEEEE" +
                        "REYEEEE" +
                        "REYEEER"));
    }

    @Test
    void test_dropDisc1() {
        Board board = new Board(Arrays.asList(
                Arrays.asList(RED, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(YELLOW, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(YELLOW, EMPTY, RED, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(YELLOW, EMPTY, YELLOW, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(RED, EMPTY, YELLOW, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(RED, EMPTY, YELLOW, EMPTY, EMPTY, EMPTY, RED)
        ));

        CodedBoard codedBoard = CodedBoard.from(board);

        CodedBoard codedBoardWithDiskDropped = codedBoard.dropDisc(1, CodedBoard.CODE_YELLOW);

        assertThat(codedBoardWithDiskDropped.getBoardCode(), is(
                "REEEEEE" +
                        "YEEEEEE" +
                        "YEREEEE" +
                        "YEYEEEE" +
                        "REYEEEE" +
                        "RYYEEER"));
    }

    @Test
    void test_dropDisc2() {
        Board board = new Board(Arrays.asList(
                Arrays.asList(RED, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(YELLOW, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(YELLOW, EMPTY, RED, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(YELLOW, EMPTY, YELLOW, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(RED, EMPTY, YELLOW, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(RED, EMPTY, YELLOW, EMPTY, EMPTY, EMPTY, RED)
        ));

        CodedBoard codedBoard = CodedBoard.from(board);

        CodedBoard codedBoardWithDiskDropped = codedBoard.dropDisc(2, CODE_RED);

        assertThat(codedBoardWithDiskDropped.getBoardCode(), is(
                "REEEEEE" +
                        "YEREEEE" +
                        "YEREEEE" +
                        "YEYEEEE" +
                        "REYEEEE" +
                        "REYEEER"));
    }

    @Test
    void test_getCell() {
        Board board = new Board(Arrays.asList(
                Arrays.asList(RED, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, YELLOW),
                Arrays.asList(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(YELLOW, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, RED)
        ));

        CodedBoard codedBoard = CodedBoard.from(board);

        assertThat(codedBoard.getBoardCode(), is(
                "REEEEEY" +
                        "EEEEEEE" +
                        "EEEEEEE" +
                        "EEEEEEE" +
                        "EEEEEEE" +
                        "YEEEEER"));

        assertThat(codedBoard.getCell(0,0), is(CODE_RED));
        assertThat(codedBoard.getCell(0, 6), is(CODE_YELLOW));
        assertThat(codedBoard.getCell(5, 0), is(CODE_YELLOW));
        assertThat(codedBoard.getCell(5, 6), is(CODE_RED));
    }


    @Test
    void test_isColumnFree() {
        Board board = new Board(Arrays.asList(
                Arrays.asList(EMPTY, RED, YELLOW, EMPTY, EMPTY, RED, EMPTY),
                Arrays.asList(EMPTY, RED, YELLOW, EMPTY, YELLOW, YELLOW, EMPTY),
                Arrays.asList(EMPTY, RED, YELLOW, EMPTY, RED, RED, EMPTY),
                Arrays.asList(EMPTY, RED, YELLOW, RED, YELLOW, YELLOW, EMPTY),
                Arrays.asList(EMPTY, RED, YELLOW, YELLOW, RED, RED, RED),
                Arrays.asList(EMPTY, RED, YELLOW, YELLOW, RED, YELLOW, YELLOW)
        ));

        CodedBoard codedBoard = CodedBoard.from(board);

        assertThat(codedBoard.getBoardCode(), is(
                "ERYEERE" +
                        "ERYEYYE" +
                        "ERYERRE" +
                        "ERYRYYE" +
                        "ERYYRRR" +
                        "ERYYRYY"));

        assertThat(codedBoard.isColumnFree(0), is(true));
        assertThat(codedBoard.isColumnFree(1), is(false));
        assertThat(codedBoard.isColumnFree(2), is(false));
        assertThat(codedBoard.isColumnFree(3), is(true));
        assertThat(codedBoard.isColumnFree(4), is(true));
        assertThat(codedBoard.isColumnFree(5), is(false));
        assertThat(codedBoard.isColumnFree(6), is(true));
    }

    @Test
    void test_isFull_false() {
        Board board = new Board(Arrays.asList(
                Arrays.asList(EMPTY, RED, YELLOW, EMPTY, EMPTY, RED, EMPTY),
                Arrays.asList(EMPTY, RED, YELLOW, EMPTY, YELLOW, YELLOW, EMPTY),
                Arrays.asList(EMPTY, RED, YELLOW, EMPTY, RED, RED, EMPTY),
                Arrays.asList(EMPTY, RED, YELLOW, RED, YELLOW, YELLOW, EMPTY),
                Arrays.asList(EMPTY, RED, YELLOW, YELLOW, RED, RED, RED),
                Arrays.asList(EMPTY, RED, YELLOW, YELLOW, RED, YELLOW, YELLOW)
        ));

        CodedBoard codedBoard = CodedBoard.from(board);

        assertThat(codedBoard.isFull(), is(false));
    }

    @Test
    void test_isFull_true() {
        Board board = new Board(Arrays.asList(
                Arrays.asList(YELLOW, RED, YELLOW, YELLOW, EMPTY, RED, YELLOW),
                Arrays.asList(YELLOW, RED, YELLOW, RED, YELLOW, YELLOW, RED),
                Arrays.asList(RED, RED, YELLOW, YELLOW, RED, RED, RED),
                Arrays.asList(RED, RED, YELLOW, RED, YELLOW, YELLOW, YELLOW),
                Arrays.asList(RED, RED, YELLOW, YELLOW, RED, RED, RED),
                Arrays.asList(RED, RED, YELLOW, YELLOW, RED, YELLOW, YELLOW)
        ));

        CodedBoard codedBoard = CodedBoard.from(board);

        assertThat(codedBoard.isFull(), is(true));
    }

    @Test
    void test_isWinning_simpleHorizontal() {
        Board board = new Board(Arrays.asList(
                Arrays.asList(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(EMPTY, YELLOW, YELLOW, YELLOW, YELLOW, EMPTY, EMPTY),
                Arrays.asList(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY)
        ));

        CodedBoard codedBoard = CodedBoard.from(board);


        assertThat(codedBoard.isWinning(CODE_YELLOW), is(true));
        assertThat(codedBoard.isWinning(CODE_RED), is(false));
    }

    @Test
    void test_isWinning_simpleVertical() {
        Board board = new Board(Arrays.asList(
                Arrays.asList(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(EMPTY, EMPTY, EMPTY, RED, EMPTY, EMPTY, EMPTY),
                Arrays.asList(EMPTY, EMPTY, EMPTY, RED, EMPTY, EMPTY, EMPTY),
                Arrays.asList(EMPTY, EMPTY, EMPTY, RED, EMPTY, EMPTY, EMPTY),
                Arrays.asList(EMPTY, EMPTY, EMPTY, RED, EMPTY, EMPTY, EMPTY),
                Arrays.asList(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY)
        ));

        CodedBoard codedBoard = CodedBoard.from(board);


        assertThat(codedBoard.isWinning(CODE_RED), is(true));
        assertThat(codedBoard.isWinning(CODE_YELLOW), is(false));
    }

    @Test
    void test_isWinning_simpleAscendingDiagonal() {
        Board board = new Board(Arrays.asList(
                Arrays.asList(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, RED),
                Arrays.asList(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, RED, EMPTY),
                Arrays.asList(EMPTY, EMPTY, EMPTY, EMPTY, RED, EMPTY, EMPTY),
                Arrays.asList(EMPTY, EMPTY, EMPTY, RED, EMPTY, EMPTY, EMPTY)
        ));

        CodedBoard codedBoard = CodedBoard.from(board);


        assertThat(codedBoard.isWinning(CODE_RED), is(true));
        assertThat(codedBoard.isWinning(CODE_YELLOW), is(false));
    }

    @Test
    void test_isWinning_simpleDescendingDiagonal() {
        Board board = new Board(Arrays.asList(
                Arrays.asList(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(EMPTY, YELLOW, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(EMPTY, EMPTY, YELLOW, EMPTY, EMPTY, EMPTY, EMPTY),
                Arrays.asList(EMPTY, EMPTY, EMPTY, YELLOW, EMPTY, EMPTY, EMPTY),
                Arrays.asList(EMPTY, EMPTY, EMPTY, EMPTY, YELLOW, EMPTY, EMPTY),
                Arrays.asList(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY)
        ));

        CodedBoard codedBoard = CodedBoard.from(board);


        assertThat(codedBoard.isWinning(CODE_RED), is(false));
        assertThat(codedBoard.isWinning(CODE_YELLOW), is(true));
    }
}
