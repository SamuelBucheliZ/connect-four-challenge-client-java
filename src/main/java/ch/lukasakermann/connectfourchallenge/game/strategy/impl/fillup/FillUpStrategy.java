package ch.lukasakermann.connectfourchallenge.game.strategy.impl.fillup;

import ch.lukasakermann.connectfourchallenge.connectFourService.dto.Game;
import ch.lukasakermann.connectfourchallenge.game.strategy.ConnectFourStrategy;

import java.util.Random;

public class FillUpStrategy implements ConnectFourStrategy {

    private final Random random;

    private FillMode fillMode;
    private int currentColumn;

    public FillUpStrategy() {
        this.random = new Random();
    }

    @Override
    public int dropDisc(Game game) {
        pickNextFreeColumn(game);
        int pickedColumn = this.currentColumn;
        if (FillMode.ROW.equals(fillMode)) {
            increaseCurrentColumn();
        }
        return pickedColumn;
    }

    private void pickNextFreeColumn(Game game) {
        while (! game.getBoard().isColumnFree(currentColumn) ) {
            increaseCurrentColumn();
        }
    }

    @Override
    public void win(Game game) {
        reset();
    }

    @Override
    public void lose(Game game) {
        reset();
    }

    @Override
    public void draw(Game game) {
        reset();
    }

    private void reset() {
        switch (this.random.nextInt(2)) {
            case 0:
                this.fillMode = FillMode.COLUMN;
                break;
            case 1:
                this.fillMode = FillMode.ROW;
                break;
        }
        this.currentColumn = this.random.nextInt(7);

    }

    private void increaseCurrentColumn() {
        this.currentColumn = (this.currentColumn + 1) % 7;
    }

    private enum FillMode {
        COLUMN, ROW
    }
}
