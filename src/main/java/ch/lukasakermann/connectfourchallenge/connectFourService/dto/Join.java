package ch.lukasakermann.connectfourchallenge.connectFourService.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Join {

    private final String playerId;

    @JsonCreator
    public Join(@JsonProperty("playerId") String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }
}
