package ch.lukasakermann.connectfourchallenge.game;

import ch.lukasakermann.connectfourchallenge.connectFourService.ConnectFourAdapter;
import ch.lukasakermann.connectfourchallenge.connectFourService.dto.Game;
import ch.lukasakermann.connectfourchallenge.connectFourService.dto.GameId;
import ch.lukasakermann.connectfourchallenge.game.strategy.ConnectFourStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;


public class GameRunner implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameRunner.class);
    private static final int POLLING_IN_MILLIS = 0;
    private final ConnectFourAdapter connectFourAdapter;
    private final String playerId;
    private final int numberOfGames;
    private final ConnectFourStrategy connectFourStrategy;


    public GameRunner(ConnectFourAdapter connectFourAdapter, String playerId, ConnectFourStrategy connectFourStrategy, int numberOfGames) {
        this.connectFourAdapter = connectFourAdapter;
        this.playerId = playerId;
        this.connectFourStrategy = connectFourStrategy;
        this.numberOfGames = numberOfGames;
    }

    @Override
    public void run() {
        LocalDateTime startTime = LocalDateTime.now();
        GamesSummary gamesSummary = new GamesSummary();
        try {
            for (int i = 0; i < numberOfGames; i++) {
                GameOutcome outcome = playOneGame();
                gamesSummary.add(outcome);
            }
            LocalDateTime endTime = LocalDateTime.now();
            long durationInSeconds = Duration.between(startTime, endTime).getSeconds();
            LOGGER.info("{}: Games are finished: won {}, lost {}, draw {}, duration {}s", playerId, gamesSummary.getGamesWon(), gamesSummary.getGamesLost(), gamesSummary.getGamesDraw(), durationInSeconds);
        } catch (InterruptedException e) {
            LOGGER.error("{}: Games are interrupted", playerId, e);
        } catch (Exception e) {
            LOGGER.error("{}: Error while playing game", playerId, e);
        }
    }

    private GameOutcome playOneGame() throws InterruptedException {
        GameId join = connectFourAdapter.getJoin(playerId);
        while (join.gameId() == null) {
            Thread.sleep(POLLING_IN_MILLIS);
            join = connectFourAdapter.getJoin(playerId);
        }
        UUID gameId = join.gameId();

        Game game = connectFourAdapter.getGame(gameId);
        while (!game.isFinished()) {
            if (game.getCurrentPlayerId().equals(playerId)) {
                pickAndDropDisc(gameId, game);
            }
            Thread.sleep(POLLING_IN_MILLIS);
            game = connectFourAdapter.getGame(gameId);
        }
        GameOutcome outcome = null;
        if (game.getWinner() == null) {
            connectFourStrategy.draw(game);
            outcome = GameOutcome.DRAW;
        } else if (playerId.equals(game.getWinner())) {
            connectFourStrategy.win(game);
            outcome = GameOutcome.WON;
        } else if (!playerId.equals(game.getWinner())) {
            connectFourStrategy.lose(game);
            outcome = GameOutcome.LOST;
        }
        return outcome;
    }

    private void pickAndDropDisc(UUID gameId, Game game) {
        long millisBeforePick = System.currentTimeMillis();
        int pickedColumn = connectFourStrategy.dropDisc(game);
        long millisAfterPick = System.currentTimeMillis();
        LOGGER.info("{}: Picked column {} in {}ms", playerId, pickedColumn, millisAfterPick-millisBeforePick);
        connectFourAdapter.dropDisc(gameId, pickedColumn, playerId);
    }
}
