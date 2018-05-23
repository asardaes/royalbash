package de.schramm.royalbash.data;

import de.schramm.royalbash.model.SummoningDeck;

import java.util.UUID;

import static de.schramm.royalbash.data.SummoningCardData.VETERAN_KNIGHT;
import static de.schramm.royalbash.data.SummoningCardData.YOUTHFUL_KNIGHT;

public class SummoningDeckData {

    public static final SummoningDeck SUMMONING_DECK_1 = SummoningDeck.builder()
            .id(UUID.fromString("b68ba1e2-c8bf-470d-8172-ef1632482fe8"))
            .card(YOUTHFUL_KNIGHT)
            .build();

    public static final SummoningDeck SUMMONING_DECK_2 = SummoningDeck.builder()
            .id(UUID.fromString("924a99ad-01af-4226-955d-b56b792ee01e"))
            .card(VETERAN_KNIGHT)
            .build();
}