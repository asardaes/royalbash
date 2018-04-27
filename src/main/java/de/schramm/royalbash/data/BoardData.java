package de.schramm.royalbash.data;

import de.schramm.royalbash.model.Board;
import de.schramm.royalbash.model.Turn;

import java.util.*;

import static de.schramm.royalbash.data.PlayerData.PLAYER_1;
import static de.schramm.royalbash.data.PlayerData.PLAYER_2;

public class BoardData {

    public static Set<Board> getBoardSet() {

        return new HashSet<>(
                Collections.singletonList(
                        Board.builder()
                                .id(UUID.fromString("b030c982-ed9b-4fc4-89b0-377ecf5d228d"))
                                .turn(
                                        Turn.builder()
                                                .counter(1)
                                                .player(PLAYER_1)
                                                .build()
                                )
                                .playerBlue(PLAYER_1)
                                .playerRed(PLAYER_2)
                                .build()
                )
        );
    }
}
