package ch.lukasakermann.connectfourchallenge.game.strategy.impl.alphabeta;

import ch.lukasakermann.connectfourchallenge.connectFourService.dto.Game;

import java.util.*;

class GameNode {

    private final CodedBoard board;
    private final char playerColorCode;
    private final boolean maximizingPlayer;
    private final Map<Integer, GameNode> children;

    private int alpha;
    private int value;
    private int beta;

    private GameNode(
            CodedBoard board,
            char playerColorCode,
            boolean maximizingPlayer,
            Map<Integer, GameNode> children,
            int alpha,
            int value,
            int beta
    ) {
        this.board = board;
        this.playerColorCode = playerColorCode;
        this.maximizingPlayer = maximizingPlayer;
        this.children = children;
        this.alpha = alpha;
        this.value = value;
        this.beta = beta;
    }

    static GameNode rootFrom(Game game) {
        CodedBoard board = CodedBoard.from(game.getBoard());
        char playerColorCode = GameEncodingUtil.getPlayerColorCode(game);
        boolean maximizingPlayer = true;
        Map<Integer, GameNode> children = new HashMap<>();
        int alpha = Integer.MIN_VALUE;
        int value = 0;
        int beta = Integer.MAX_VALUE;
        return new GameNode(
          board,
          playerColorCode,
          maximizingPlayer,
          children,
          alpha,
          value,
          beta
        );
    }

    private static GameNode childFrom(GameNode parent, int column) {
        char playerColorCode = GameEncodingUtil.getOppositeColorCode(parent.playerColorCode);
        CodedBoard board = parent.board.dropDisc(column, playerColorCode);
        boolean maximizingPlayer = !parent.maximizingPlayer;
        Map<Integer, GameNode> children = new HashMap<>();
        int alpha = parent.alpha;
        int beta = parent.beta;
        int value;
        if (maximizingPlayer) {
            value = Integer.MIN_VALUE;
        } else {
            value = Integer.MAX_VALUE;
        }
        return new GameNode(
                board,
                playerColorCode,
                maximizingPlayer,
                children,
                alpha,
                beta,
                value
        );
    }

    int alphabeta(int depth) {
        if (depth == 0 || board.isTerminal()) {
            return calculateValueOfNode();
        }
        if (maximizingPlayer) {
              for (int move = 0; move < board.getNumberOfColumns(); move++) {
                  if (board.isColumnFree(move)) {
                      GameNode child = getOrCreateChild(move);
                      int childValue = child.alphabeta(depth - 1);
                      value = Math.max(value, childValue);
                      alpha = Math.max(alpha, value);
                      if (alpha >= beta) {
                          break;
                      }
                  }
            }
            return value;
        } else {
            for (int move = 0; move < board.getNumberOfColumns(); move++) {
                if (board.isColumnFree(move)) {
                    GameNode child = getOrCreateChild(move);
                    int childValue = child.alphabeta(depth - 1);
                    value = Math.min(value, childValue);
                    beta = Math.min(beta, value);
                    if (alpha >= beta) {
                        break;
                    }
                }
            }
            return value;
        }
    }

    private GameNode getOrCreateChild(int move) {
        return children.computeIfAbsent(move, i -> childFrom(this, i));
    }

    private int calculateValueOfNode() {
        if (board.isWinning(playerColorCode)) {
            return 5;
        } else if (board.isWinning(GameEncodingUtil.getOppositeColorCode(playerColorCode))) {
            return -5;
        } else {
            return 0;
        }
    }

    int getMaxValueChild() {
        Comparator<Map.Entry<Integer, GameNode>> entryComparator = Comparator.<Map.Entry<Integer, GameNode>, Integer>comparing(e -> e.getValue().value).reversed();
        return children.entrySet().stream()
                .sorted(entryComparator)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(0);
    }
}
