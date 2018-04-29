package de.schramm.royalbash.controller;

import de.schramm.royalbash.controller.requestmodel.PlayerRequest;
import de.schramm.royalbash.controller.responsemodel.AccountExt;
import de.schramm.royalbash.model.Account;
import de.schramm.royalbash.persistence.blueprint.BlueprintRepository;
import de.schramm.royalbash.persistence.account.AccountRepository;
import de.schramm.royalbash.persistence.account.AccountRepositoryFake;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

public class AccountEntityControllerTest {

    private final String playerName = "testName";
    private final String playerEmail = "text@mail.com";
    private final String playerPasswordHash = "123qwe";

    private final Account account = Account.builder()
            .id(UUID.randomUUID())
            .name(playerName)
            .email(playerEmail)
            .passwordHash(playerPasswordHash)
            .build();

    private final BlueprintRepository blueprintRepository = mock(BlueprintRepository.class);

    private final AccountRepository accountRepository = new AccountRepositoryFake(blueprintRepository);

    private final AccountController accountController = new AccountController(accountRepository);

    {
        accountRepository.save(account);
    }

    @Test
    public void login_existing_Player_name_and_email() {

        // When

        ResponseEntity<AccountExt> playerExtResponseEntity = accountController.login(
                PlayerRequest.builder()
                        .name(playerName)
                        .email(playerEmail)
                        .passwordHash(playerPasswordHash)
                        .build()
        );

        // Then

        Assert.assertThat(playerExtResponseEntity.getBody(), is(AccountExt.fromAccount(account)));
    }

    @Test
    public void login_existing_Player_missing_email() {

        // When

        ResponseEntity<AccountExt> playerExtResponseEntity = accountController.login(
                PlayerRequest.builder()
                        .name(playerName)
                        .passwordHash(playerPasswordHash)
                        .build()
        );

        // Then

        Assert.assertThat(playerExtResponseEntity.getBody(), is(AccountExt.fromAccount(account)));
    }

    @Test
    public void login_existing_Player_missing_name() {

        // When

        ResponseEntity<AccountExt> playerExtResponseEntity = accountController.login(
                PlayerRequest.builder()
                        .email(playerEmail)
                        .passwordHash(playerPasswordHash)
                        .build()
        );

        // Then

        Assert.assertThat(playerExtResponseEntity.getBody(), is(AccountExt.fromAccount(account)));
    }

    @Test
    public void login_existing_Player_wrong_password() {

        // When

        ResponseEntity<AccountExt> playerExtResponseEntity = accountController.login(
                PlayerRequest.builder()
                        .name(playerName)
                        .email(playerEmail)
                        .passwordHash("qwe123")
                        .build()
        );

        // Then

        Assert.assertThat(playerExtResponseEntity, is(ResponseEntity.badRequest().body(AccountExt.builder().build())));
    }

    @Test
    public void login_non_existing_Player() {

        // When

        ResponseEntity<AccountExt> playerExtResponseEntity = accountController.login(
                PlayerRequest.builder()
                        .name("otherName")
                        .email("otherEmail")
                        .passwordHash("qwe123")
                        .build()
        );

        // Then

        Assert.assertThat(playerExtResponseEntity, is(ResponseEntity.badRequest().body(AccountExt.builder().build())));
    }

    @Test
    public void register_non_existing_Player() {

        // Given

        accountRepository.delete(account.getId());

        // When

        ResponseEntity responseEntity = accountController.register(
                PlayerRequest.builder()
                        .name(playerName)
                        .email(playerEmail)
                        .passwordHash(playerPasswordHash)
                        .build()
        );

        // Then

        Assert.assertThat(responseEntity, is(ResponseEntity.ok().build()));
    }

    @Test
    public void register_existing_Player() {

        // When

        ResponseEntity responseEntity = accountController.register(
                PlayerRequest.builder()
                        .name(playerName)
                        .email(playerEmail)
                        .passwordHash(playerPasswordHash)
                        .build()
        );

        // Then

        Assert.assertThat(responseEntity, is(ResponseEntity.badRequest().build()));
    }
}