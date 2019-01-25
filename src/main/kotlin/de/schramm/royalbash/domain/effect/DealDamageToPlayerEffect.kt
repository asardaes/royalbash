package de.schramm.royalbash.domain.effect

import de.schramm.royalbash.domain.Context
import de.schramm.royalbash.domain.Game
import de.schramm.royalbash.domain.Player

data class DealDamageToPlayerEffect (private val amountOfDamage: Int) {

    operator fun invoke(context: Context): Game {

        return context.targetPlayer
                ?.let { context.game
                            .findPlayer(it)
                            ?.setHitpoints(it.hitpoints - amountOfDamage)
                            ?.updateInGame(context.game, context.targetPlayer) }
                ?: context.game
    }

    private fun Player.updateInGame(game: Game, oldPlayer: Player): Game = game.updatePlayer(oldPlayer, this)

}
