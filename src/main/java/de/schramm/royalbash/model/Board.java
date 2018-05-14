package de.schramm.royalbash.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Builder
@Getter
@ToString
public class Board {

    private final UUID id;
    private final Turn turn;
    private final Player playerBlue;
    private final Player playerRed;


    public void bury(Summoning summoning) {

        playerBlue.bury(summoning);
        playerRed.bury(summoning);
    }
}
