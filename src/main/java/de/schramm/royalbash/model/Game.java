package de.schramm.royalbash.model;

import de.schramm.royalbash.gameengine.exception.DomainObjectDoesNotExistException;
import lombok.Builder;
import lombok.Value;
import lombok.val;
import org.springframework.data.annotation.Id;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Stream;

@Value
@Builder
public class Game {

    @Id
    private UUID id;
    private UUID accountRed;
    private UUID accountBlue;
    private Board board;

    public Player findPlayer(UUID playerId) throws DomainObjectDoesNotExistException{

        try {

            return board.getPlayerRed().getId().equals(playerId) ? board.getPlayerRed() : board.getPlayerBlue();
        } catch(NullPointerException e) {

            throw new DomainObjectDoesNotExistException(
                    String.format(
                            "Player %s does not exist",
                            playerId
                    )
            );
        }
    }

    public SummoningCard findHandCard(UUID cardId) throws DomainObjectDoesNotExistException {

        try {

            val playerRedCards = board.getPlayerRed().getHand().getSummoningCards();
            val playerBlueCards = board.getPlayerBlue().getHand().getSummoningCards();

            return Stream.of(playerRedCards, playerBlueCards)
                    .flatMap(Collection::stream)
                    .filter(card -> card.getId().equals(cardId))
                    .findFirst()
                    .orElseThrow(NullPointerException::new);
        } catch (NullPointerException e) {

            throw new DomainObjectDoesNotExistException(
                    String.format(
                            "Hand SummoningCard %s does not exist",
                            cardId
                    )
            );
        }
    }

    public Target findTarget(UUID targetId) throws DomainObjectDoesNotExistException {

        try {

            val playerRedTargets = board.getPlayerRed().getField().getTargets();
            val playerBlueTargets = board.getPlayerBlue().getField().getTargets();

            return Stream.of(playerRedTargets, playerBlueTargets)
                    .flatMap(Collection::stream)
                    .filter(target -> target.getId().equals(targetId))
                    .findFirst()
                    .orElseThrow(NullPointerException::new);
        } catch (NullPointerException e) {

            throw new DomainObjectDoesNotExistException(
                    String.format(
                            "Target %s does not exist",
                            targetId
                    )
            );
        }
    }
}
