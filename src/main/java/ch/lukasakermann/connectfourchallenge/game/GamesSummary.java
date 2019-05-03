package ch.lukasakermann.connectfourchallenge.game;

class GamesSummary {

    private int gamesWon = 0;
    private int gamesLost = 0;
    private int gamesDraw = 0;

    void add(GameOutcome outcome) {
        switch (outcome) {
            case WON:
                this.gamesWon += 1;
                break;
            case LOST:
                this.gamesLost += 1;
                break;
            case DRAW:
                this.gamesDraw += 1;
                break;
        }
    }

    int getGamesWon() {
        return gamesWon;
    }

    int getGamesLost() {
        return gamesLost;
    }

    int getGamesDraw() {
        return gamesDraw;
    }
}
