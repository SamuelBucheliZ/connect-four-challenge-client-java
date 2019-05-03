package ch.lukasakermann.connectfourchallenge.game.strategy;

import ch.lukasakermann.connectfourchallenge.connectFourService.dto.Game;

public interface ConnectFourStrategy {
    int dropDisc(Game game);

    default void win(Game game) {
    }

    default void lose(Game game) {
    }

    default void draw(Game game) {
    }
}
