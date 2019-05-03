package ch.lukasakermann.connectfourchallenge.game.strategy.impl.alphabeta;

import ch.lukasakermann.connectfourchallenge.connectFourService.dto.Game;
import ch.lukasakermann.connectfourchallenge.game.strategy.ConnectFourStrategy;

public class AlphaBetaStrategy implements ConnectFourStrategy {

    private static final int DEFAULT_DEPTH = 5;

    private final int maxDepth;

    private AlphaBetaStrategy(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public static AlphaBetaStrategy withDefaultDepth() {
        return new AlphaBetaStrategy(DEFAULT_DEPTH);
    }

    public static AlphaBetaStrategy withDepth(int maxDepth) {
        return new AlphaBetaStrategy(maxDepth);
    }

    @Override
    public int dropDisc(Game game) {
        GameNode node = GameNode.rootFrom(game);
        node.alphabeta(maxDepth);
        return node.getMaxValueChild();
    }


}
