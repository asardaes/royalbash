package de.schramm.royalbash.core.domain.card.summoningcard;

import de.schramm.royalbash.core.domain.card.summoningcard.attackingtarget.MountedAttackingTargetEffect;

import java.util.UUID;

public class YouthfulKnight extends AbstractSummoningCard {

    public YouthfulKnight() {

        super(
                UUID.fromString("c31a66c7-2f76-4e81-a922-835272833967"),
                "Youthful Knight",
                "/img/youthful_knight.png",
                "Creature",
                "Knight",
                "<b>Mounted</b> <i>(When attacking, the defending Summoning can be slain before dealing damage)</i>",
                "Pride goes before a fall",
                2,
                1,
                0,
                2,
                1
        );

        addTag(Tag.MOUNTED);
        setAttackingTargetEffect(new MountedAttackingTargetEffect());
    }
}
