package de.schramm.royalbash.application

import de.schramm.royalbash.domain.Game
import de.schramm.royalbash.domain.Player
import de.schramm.royalbash.domain.State
import de.schramm.royalbash.domain.card.ByondInsight
import de.schramm.royalbash.domain.card.Fireball
import de.schramm.royalbash.domain.card.LightningBolt
import de.schramm.royalbash.domain.card.MemoryLeak
import de.schramm.royalbash.domain.card.creature.GoblinRaider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
open class TestDataLoader @Autowired constructor(private val repository: GameRepository) {

    @PostConstruct
    fun loadTestData() {

        val goblinRaider = GoblinRaider(
                id = "goblinRaider",
                cost = 2,
                attack = 2,
                hitpoints = 1)
        val fireball = Fireball(
                id = "fireball",
                cost = 2)
        val memoryLeak = MemoryLeak(
                id = "memoryleak",
                cost = 2)
        val byondInsight = ByondInsight(
                id = "byondInsight",
                cost = 2)
        val lightningBolt = LightningBolt(
                id = "lightningBolt",
                cost = 2)
        val player1 = Player(
                id = "player1",
                name = "Player 1",
                hitpoints = 25,
                handcards = listOf(goblinRaider, fireball, memoryLeak),
                deckcards = listOf(byondInsight),
                depositcards = listOf(lightningBolt))
        val player2 = Player(
                id = "player2",
                name = "Player 2")
        val game = Game(
                id = "1",
                player1 = player1,
                player2 = player2,
                playerOnTurn = player1,
                state = State.OPEN)

        repository.save(game)
    }
}