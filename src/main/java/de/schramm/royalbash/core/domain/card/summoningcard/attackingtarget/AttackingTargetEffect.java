package de.schramm.royalbash.core.domain.card.summoningcard.attackingtarget;

import de.schramm.royalbash.core.domain.game.board.player.field.Summoning;
import de.schramm.royalbash.core.domain.game.board.player.field.Target;
import de.schramm.royalbash.core.exception.GameEngineException;
import de.schramm.royalbash.core.domain.card.EffectContext;

import java.util.UUID;

public interface AttackingTargetEffect {

    void apply(
            Summoning attackingSummoning,
            Target attackedTarget
    ) throws GameEngineException;
}
