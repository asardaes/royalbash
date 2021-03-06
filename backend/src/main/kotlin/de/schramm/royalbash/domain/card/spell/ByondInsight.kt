package de.schramm.royalbash.domain.card.spell

import de.schramm.royalbash.domain.Card
import de.schramm.royalbash.domain.Context
import de.schramm.royalbash.domain.Game
import de.schramm.royalbash.domain.effect.DrawHandcardsEffect

data class ByondInsight(
        override val id: String,
        override val cost: Int
) : Card {

    override val name = "Beyond Insight"
    override val text = "Target player draws two cards."
    override val image: String? = null
    val effect = DrawHandcardsEffect(2)

    override fun invoke(context: Context): Game {
        return effect.invoke(context)
    }
}
