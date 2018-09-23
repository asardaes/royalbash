package de.schramm.royalbash.controller.service.core;

import de.schramm.royalbash.controller.service.core.card.CardMock;
import de.schramm.royalbash.controller.service.core.card.creature.CreatureMock;
import lombok.val;
import org.assertj.core.data.Index;
import org.junit.Test;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerTest {

    @Test
    public void should_deliver_hitpoints() {

        // Given
        val testee = Player.builder()
                .hitpoints(30)
                .build();

        // When
        val hitpoints = testee.getHitpoints();

        // Then
        assertThat(hitpoints).isEqualTo(30);
    }

    @Test
    public void should_change_hitpoints() {

        // Given
        val testee = Player.builder()
                .hitpoints(30)
                .build();

        // When
        val player = testee.setHitpoints(10);

        // Then
        assertThat(player.getHitpoints()).isEqualTo(10);
    }

    @Test
    public void should_deliver_handcards() {

        // Given
        val handcard = CardMock.builder().build();
        val testee = Player.builder()
                .handcard(handcard)
                .build();

        // When
        val cards = testee.getHandcards();

        // Then
        assertThat(cards)
                .hasSize(1)
                .contains(handcard);
    }

    @Test
    public void should_deliver_handcards_in_order() {

        // Given
        val handcard1 = CardMock.builder()
                .name("Card 1")
                .build();
        val handcard2 = CardMock.builder()
                .name("Card 2")
                .build();
        val testee = Player.builder()
                .handcard(handcard1)
                .handcard(handcard2)
                .build();

        // When
        val cards = testee.getHandcards().collect(Collectors.toList());

        // Then
        assertThat(cards)
                .contains(handcard1, Index.atIndex(0))
                .contains(handcard2, Index.atIndex(1));
    }

    @Test
    public void should_deliver_deposit() {

        // Given
        val card = CardMock.builder().build();
        val testee = Player.builder()
                .depositcard(card)
                .build();

        // When
        val cards = testee.getDepositcards();

        // Then
        assertThat(cards)
                .hasSize(1)
                .contains(card);
    }

    @Test
    public void should_remove_handcard_and_add_it_ro_deposit() {

        // Given
        val card = CardMock.builder().build();
        val testee = Player.builder()
                .handcard(card)
                .build();

        // When
        val player = testee.removeHandcard(card);

        // Then
        assertThat(player.getHandcards()).hasSize(0);
        assertThat(player.getDepositcards())
                .hasSize(1)
                .contains(card);
    }

    @Test
    public void should_not_remove_handcard_if_it_cannot_be_found() {

        // Given
        val handcard1 = CardMock.builder().name("Card 1").build();
        val handcard2 = CardMock.builder().name("Card 2").build();
        val testee = Player.builder()
                .handcard(handcard1)
                .build();

        // When
        val player = testee.removeHandcard(handcard2);

        // Then
        assertThat(player.getHandcards())
                .hasSize(1)
                .contains(handcard1);
    }

    @Test
    public void should_retain_order_when_removing_card() {

        // Given
        val handcard1 = CardMock.builder().name("Card 1").build();
        val handcard2 = CardMock.builder().name("Card 2").build();
        val handcard3 = CardMock.builder().name("Card 3").build();
        val testee = Player.builder()
                .handcard(handcard1)
                .handcard(handcard2)
                .handcard(handcard3)
                .build();

        // When
        val player = testee.removeHandcard(handcard2);

        // Then
        assertThat(player.getHandcards())
                .hasSize(2)
                .contains(handcard1, Index.atIndex(0))
                .contains(handcard3, Index.atIndex(1));
    }

    @Test
    public void should_remove_creature_and_add_it_to_deposit() {

        // Given
        val creature = CreatureMock.builder().build();
        val spot = Spot.builder()
                .creature(creature)
                .build();
        val testee = Player.builder()
                .spot(spot)
                .build();

        // When
        val player = testee.removeCreature(creature);

        // Then
        val creaturesOfPlayer = player.getSpots()
                .map(Spot::getCreature)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        assertThat(creaturesOfPlayer).isEmpty();
        assertThat(player.getDepositcards())
                .hasSize(1)
                .contains(creature);
    }

    @Test
    public void should_not_remove_creature() {

        // Given
        val creature = CreatureMock.builder().build();
        val spot = Spot.builder()
                .creature(creature)
                .build();
        val testee = Player.builder()
                .spot(spot)
                .build();

        // When
        val player = testee.removeCreature(
                CreatureMock.builder()
                        .hitpoints(2)
                        .build()
        );

        // Then
        val creaturesOfPlayer = player.getSpots()
                .map(Spot::getCreature)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        assertThat(creaturesOfPlayer).hasSize(1);
        assertThat(creaturesOfPlayer).contains(creature);
    }

    @Test
    public void should_update_creature() {

        // Given
        val creature = CreatureMock.builder().build();
        val spot = Spot.builder()
                .creature(creature)
                .build();
        val testee = Player.builder()
                .spot(spot)
                .build();
        val updatedCreature = creature.toBuilder()
                .hitpoints(12)
                .build();

        // When
        val player = testee.updateCreature(creature, updatedCreature);

        // Then
        val creaturesOfPlayer = player.getSpots()
                .map(Spot::getCreature)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        assertThat(creaturesOfPlayer).contains(updatedCreature);
        assertThat(creaturesOfPlayer).doesNotContain(creature);
    }

    @Test
    public void should_not_update_creature() {

        // Given
        val creature = CreatureMock.builder().build();
        val spot = Spot.builder()
                .build();
        val testee = Player.builder()
                .spot(spot)
                .build();

        // When
        val player = testee.updateCreature(creature, creature);

        // Then
        val creaturesOfPlayer = player.getSpots()
                .map(Spot::getCreature)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        assertThat(creaturesOfPlayer).doesNotContain(creature);
    }

    @Test
    public void should_find_handcard() throws Exception {

        // Given
        val card = CreatureMock.builder()
                .id("Id 1")
                .build();
        val testee = Player.builder()
                .handcard(card)
                .build();

        // When
        val foundCard = testee.findHandcard("Id 1")
                .orElseThrow(() -> new Exception("Card not present"));

        // Then
        assertThat(foundCard).isEqualTo(card);
    }

    @Test
    public void should_not_find_handcard() {

        // Given
        val testee = Player.builder().build();

        // When
        val card = testee.findHandcard("Id 1");

        // Then
        assertThat(card.isPresent()).isFalse();
    }
}
