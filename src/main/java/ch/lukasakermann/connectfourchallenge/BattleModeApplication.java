package ch.lukasakermann.connectfourchallenge;

import ch.lukasakermann.connectfourchallenge.connectFourService.ConnectFourAdapter;
import ch.lukasakermann.connectfourchallenge.game.GameRunner;
import ch.lukasakermann.connectfourchallenge.game.strategy.impl.fillup.FillUpStrategy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BattleModeApplication {

    private static final String SERVER_URL = "https://connect-four-challenge.herokuapp.com";
    private static final int NUMBER_OF_GAMES = 10;

    public static void main(String[] args) {
        ConnectFourAdapter connectFourAdapter = new ConnectFourAdapter(SERVER_URL);
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(new GameRunner(connectFourAdapter, "randall", new FillUpStrategy(), NUMBER_OF_GAMES));
    }
}
