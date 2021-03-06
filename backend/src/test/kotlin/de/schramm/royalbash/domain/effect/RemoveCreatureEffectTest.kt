package de.schramm.royalbash.domain.effect

import de.schramm.royalbash.domain.Context
import de.schramm.royalbash.domain.Game
import de.schramm.royalbash.domain.Player
import de.schramm.royalbash.domain.Spot
import de.schramm.royalbash.domain.card.creature.CreatureMock
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RemoveCreatureEffectTest {

    @Test
    fun should_remove_target_creature_from_spot_an_add_it_to_deposit() {

        // Given
        val testee = RemoveCreatureEffect()
        val creature = CreatureMock("Id 1")
        val spot = Spot(id = "spot", creature = creature)
        val owner = Player("Id 2", spots = listOf(spot))
        val player2 = Player("Id 3")
        val game = Game("Id 4", player1 = owner, player2 = player2)
        val context = Context(game = game, owner = owner, targetCreature = creature)

        // When
        val updatedGame = testee.invoke(context)

        // Then
        assertThat(updatedGame).isNotNull
        assertThat(updatedGame.player1.findCreature(creature)).isNull()
        assertThat(updatedGame.player1.depositcards)
                .hasSize(1)
                .contains(creature)
    }

}
