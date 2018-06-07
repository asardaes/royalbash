package de.schramm.royalbash.gameengine.model.card.summoningcard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.schramm.royalbash.gameengine.model.Card;
import de.schramm.royalbash.gameengine.model.Summoning;
import de.schramm.royalbash.gameengine.model.Tag;
import de.schramm.royalbash.gameengine.model.card.effect.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class SummoningCard implements Card {

    private final UUID id;
    private final String name;
    private final String image;
    private final String type;
    private final String subType;
    private final String text;
    private final String lore;
    private final int costRations;
    private final int costMaterial;
    private final int costBlessing;
    private final int strength;
    private final int health;

    @JsonIgnore
    @Singular("tag")
    private final Set<Tag> tags = new HashSet<>();

    @JsonIgnore
    private GenericEffect playEffect = new PlainGenericEffect();

    @JsonIgnore
    private AttackSummoningEffect attackSummoningEffect = new PlainAttackSummoningEffect();

    @JsonIgnore
    private GenericEffect ownerTurnStartEffect = new PlainGenericEffect();

    @JsonIgnore
    private GenericEffectWithSourceSummoning ownerTurnStartEffectWithSource =
            new PlainGenericEffectWithSourceSummoning();

    public final Summoning toInstance(UUID id) {
        return Summoning.fromCard(this, id);
    }

    @Override
    public String getCardType() {
        return "SummoningCard";
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }
}
