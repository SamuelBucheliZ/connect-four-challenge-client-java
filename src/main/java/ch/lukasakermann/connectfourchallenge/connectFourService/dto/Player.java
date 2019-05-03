package ch.lukasakermann.connectfourchallenge.connectFourService.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Player {

    private String playerId;
    private DiscColor disc;

    @JsonCreator
    public Player(@JsonProperty("playerId") String playerId,
                  @JsonProperty("disc") DiscColor disc) {
        this.playerId = playerId;
        this.disc = disc;
    }

    public String getPlayerId() {
        return playerId;
    }

    public DiscColor getDisc() {
        return disc;
    }
}
