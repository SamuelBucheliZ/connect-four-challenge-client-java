package ch.lukasakermann.connectfourchallenge.game.strategy.impl.random;

import ch.lukasakermann.connectfourchallenge.connectFourService.dto.Cell;
import ch.lukasakermann.connectfourchallenge.connectFourService.dto.Game;
import ch.lukasakermann.connectfourchallenge.game.strategy.ConnectFourStrategy;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomStrategy implements ConnectFourStrategy {

    @Override
    public int dropDisc(Game game) {
        List<List<Cell>> board = game.getBoard().getBoard();
        List<Cell> columns = board.get(0);
        List<Integer> validMoves = IntStream.range(0, columns.size())
                .boxed()
                .filter(column -> columns.get(column).equals(Cell.EMPTY))
                .collect(Collectors.toList());

        Random rand = new Random();
        return validMoves.get(rand.nextInt(validMoves.size()));
    }
}
