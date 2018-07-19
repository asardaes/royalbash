package de.schramm.royalbash.core.domain.card.effect;

import de.schramm.royalbash.core.exception.RuleViolationException;
import de.schramm.royalbash.core.domain.card.EffectContext;

public interface GenericEffect {

    void apply(EffectContext context) throws RuleViolationException;
}