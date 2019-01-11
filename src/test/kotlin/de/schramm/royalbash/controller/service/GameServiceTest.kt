package de.schramm.royalbash.controller.service

import de.schramm.royalbash.controller.service.core.Game
import de.schramm.royalbash.controller.service.core.Player
import de.schramm.royalbash.controller.service.core.State.OPEN
import de.schramm.royalbash.controller.service.core.UUIDGenerator
import de.schramm.royalbash.controller.service.gameevent.GameEvent
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*

class GameServiceTest {

    @Test
    fun should_retrieve_stored_game() {

        // Given
        val gameId = "Game Id"
        val game = Game(
                gameId,
                player1 = Player("Id 1"),
                player2 = Player("Id 2"))
        val repository = mockk<GameRepository>()
        every { repository.findAll() } returns setOf(game)
        val testee = GameService(UUIDGenerator(), repository)

        // When
        val retrievedGame = testee.retrieveGame(gameId)

        // Then
        assertThat(retrievedGame)
                .isPresent
                .hasValue(game)
    }

    @Test
    fun should_create_and_store_game() {

        // Given
        val accountId1 = "Account 1"
        val accountId2 = "Account 2"
        val id = UUID.randomUUID().toString()
        val repository = GameRepositoryMock()
        val uuidGenerator = mockk<UUIDGenerator>()
        every { uuidGenerator.generateId() } returns id
        val testee = GameService(uuidGenerator, repository)

        // When
        val game = testee.createGame(accountId1, accountId2)

        // Then
        assertThat(game).isNotNull
        assertThat(game.player1.name).isEqualTo(accountId1)
        assertThat(game.player2.name).isEqualTo(accountId2)
        assertThat(repository.exists(id)).isTrue()
    }

    @Test
    fun should_retrieve_game_after_event_is_committed() {

        // Given
        val gameId = "Id 1"
        val game = Game(
                gameId,
                player1 = Player("Id 2"),
                player2 = Player("Id 3"))
        val gameEvent = mockk<GameEvent>()
        every { gameEvent.invoke(game) } returns game
        val repository = mockk<GameRepository>()
        every { repository.findAll() } returns setOf(game)
        val testee = GameService(UUIDGenerator(), repository)

        // When
        val updatedGame = testee.commitGameEvent(gameId, gameEvent)

        // Then
        assertThat(updatedGame)
                .isPresent
                .hasValue(game)
    }
}