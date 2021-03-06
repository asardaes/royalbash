package de.schramm.royalbash.infrastructure.gameevent

import de.schramm.royalbash.domain.Game

data class CreatureAttackedEventDTO(
        val attackerId: String,
        val ownerId: String,
        val defenderId: String
                                   ) : GameEventDTO {

    constructor(): this(attackerId = "", ownerId = "", defenderId = "")

    override fun invoke(game: Game): Game {

        val owner = game.findPlayer(ownerId)
        val attacker = game.findCreature(attackerId)?.takeIf { owner?.findCreature(it) == it }
        val defender = game.findCreature(defenderId)?. takeUnless { owner?.findCreature(it) == it }

        return if (owner != null && attacker != null && defender != null) {
            game.combat(attacker, owner, defender)
        } else game
    }
}
