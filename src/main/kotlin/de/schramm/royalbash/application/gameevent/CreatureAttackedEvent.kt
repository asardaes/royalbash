package de.schramm.royalbash.application.gameevent

import de.schramm.royalbash.domain.Game

data class CreatureAttackedEvent(
        val attackerId: String,
        val ownerId: String,
        val defenderId: String
) : GameEvent {

    constructor(): this(attackerId = "", ownerId = "", defenderId = "")

    override fun invoke(game: Game): Game {
        return game
                .findPlayer(ownerId)
                ?.let { game.findCreature(attackerId)
                            .map { attacker -> game
                                    .findCreature(defenderId)
                                    .map { defender -> game.combat(attacker, it, defender) }
                                    .orElse(game) }
                            .orElse(game) }
                ?: game
    }
}
