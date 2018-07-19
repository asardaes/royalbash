package de.schramm.royalbash.core.domain.game.board.player;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
class Graveyard {

    @Singular("card")
    private List<Card> cards;
}
