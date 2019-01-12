package de.schramm.royalbash.api

import de.schramm.royalbash.application.GameRepositoryMock
import de.schramm.royalbash.application.GameService
import de.schramm.royalbash.domain.Game
import de.schramm.royalbash.domain.Player
import de.schramm.royalbash.domain.State.OPEN
import de.schramm.royalbash.application.gameevent.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(value = [(GameController::class)], secure = false)
class EventTranslationTest {

    @TestConfiguration
    open class ControllerTestConfig {
        @Bean
        open fun gameService() = mockk<GameService>()
        @Bean
        open fun gameRepository() = GameRepositoryMock()
    }

    private val gameId = "1"

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var gameService: GameService

    @BeforeEach
    fun define_mock_behavior() {

        val gameOptional = Optional.of(Game(
                gameId,
                player1 = Player("Id 2"),
                player2 = Player("Id 3"),
                playerOnTurn = Player("Id 2"),
                state = OPEN))

        every { gameService.commitGameEvent(any(), any()) } returns gameOptional
    }

    @Test
    @Throws(Exception::class)
    fun should_translate_CardDrawnEvent() {

        // Given
        val json = """{
            "event": {
                "type": "CARD_DRAWN",
                "amountOfCards": 1,
                "playerId": "Player Id"
            }
        }"""
        val expectedEvent = CardDrawnEvent("Player Id", 1)

        // When Then
        test(json, expectedEvent)
    }

    @Test
    @Throws(Exception::class)
    fun should_translate_CardPlayedOnPlayerEvent() {

        // Given
        val json = "{\"event\": {" +
                        "\"type\": \"CARD_PLAYED_ON_PLAYER\", " +
                        "\"cardId\": \"Card Id\", " +
                        "\"ownerId\": \"Owner Id\", " +
                        "\"targetPlayerId\": \"Target Player Id\"}" +
                        "}"
        val expectedEvent = CardPlayedOnPlayerEvent(
                "Card Id",
                "Owner Id",
                "Target Player Id")

        // When Then
        test(json, expectedEvent)
    }

    @Test
    @Throws(Exception::class)
    fun should_translate_CreatureAttackedEvent() {

        // Given
        val json = "{\"event\": {" +
                        "\"type\": \"CREATURE_ATTACKED\", " +
                        "\"attackerId\": \"Attacker Id\", " +
                        "\"defenderId\": \"Defender Id\", " +
                        "\"ownerId\": \"Owner Id\"}" +
                        "}"
        val expectedEvent = CreatureAttackedEvent(
                attackerId = "Attacker Id",
                defenderId = "Defender Id",
                ownerId = "Owner Id")

        // When Then
        test(json, expectedEvent)
    }

    @Test
    @Throws(Exception::class)
    fun should_translate_NoOpEvent() {

        // Given
        val json = String.format(
                "{\"event\": {\"type\": \"NO_OP\"}}",
                gameId
        )
        val expectedEvent = NoOpEvent()

        // When Then
        test(json, expectedEvent)
    }

    @Test
    @Throws(Exception::class)
    fun should_translate_PlayerAttackedEvent() {

        // Given
        val json = "{\"event\": {" +
                        "\"type\": \"PLAYER_ATTACKED\", " +
                        "\"creatureId\": \"Creature Id\", " +
                        "\"ownerId\": \"Owner Id\"}" +
                        "}"
        val expectedEvent = PlayerAttackedEvent("Creature Id", "Owner Id")

        // When Then
        test(json, expectedEvent)
    }

    @Test
    @Throws(Exception::class)
    fun should_translate_TurnEndedEvent() {

        // Given
        val json = String.format(
                "{\"event\": {" +
                        "\"type\": \"TURN_ENDED\", " +
                        "\"playerId\": \"Player Id\"}" +
                        "}",
                gameId
        )
        val expectedEvent = TurnEndedEvent("Player Id")

        // When Then
        test(json, expectedEvent)
    }

    @Throws(Exception::class)
    private fun test(json: String, expectedEvent: GameEvent) {

        // Given
        val requestBuilder = MockMvcRequestBuilders
                .post("/game/1/event")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)

        // When
        mockMvc.perform(requestBuilder)
                .andReturn()
                .response
                .contentAsString

        // Then
        verify { gameService.commitGameEvent(gameId, expectedEvent) }
    }
}
