package de.schramm.royalbash.infrastructure.gameevent

import de.schramm.royalbash.domain.Game

data class NoOpEventDTO(val noopValue: String) : GameEventDTO {

    constructor(): this(noopValue = "")

    override fun invoke(game: Game) = game
}
