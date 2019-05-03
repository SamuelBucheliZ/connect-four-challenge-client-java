package ch.lukasakermann.connectfourchallenge.game.strategy.impl.alphabeta;

import ch.lukasakermann.connectfourchallenge.connectFourService.dto.DiscColor;
import ch.lukasakermann.connectfourchallenge.connectFourService.dto.Game;
import ch.lukasakermann.connectfourchallenge.connectFourService.dto.Player;

import static ch.lukasakermann.connectfourchallenge.game.strategy.impl.alphabeta.CodedBoard.CODE_RED;
import static ch.lukasakermann.connectfourchallenge.game.strategy.impl.alphabeta.CodedBoard.CODE_YELLOW;

class GameEncodingUtil {

    private GameEncodingUtil() {
        // only static methods
    }

    static char getPlayerColorCode(Game game) {
        DiscColor currentPlayerColor = getCurrentPlayerColor(game);
        switch (currentPlayerColor) {
            case RED:
                return CODE_RED;
            case YELLOW:
                return CODE_YELLOW;
            default:
                throw new IllegalArgumentException("unknown color " + currentPlayerColor);
        }
    }

    private static DiscColor getCurrentPlayerColor(Game game) {
        String currentPlayerId = game.getCurrentPlayerId();
        return game.getPlayers().stream()
                .filter(player -> player.getPlayerId().equals(currentPlayerId))
                .map(Player::getDisc)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("cannot find current player's disc color"));
    }

    static char getOppositeColorCode(char colorCode) {
        if (colorCode == CODE_RED) {
            return CODE_YELLOW;
        } else if (colorCode == CODE_YELLOW) {
            return CODE_RED;
        } else {
            throw new IllegalArgumentException("unknown color code " + colorCode);
        }
    }
}
