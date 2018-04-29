package de.schramm.royalbash.controller.responsemodel;

import de.schramm.royalbash.model.Blueprint;
import de.schramm.royalbash.model.Account;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.Set;
import java.util.UUID;

@Value
@Builder
public class AccountExt {

    private UUID id;
    private String name;

    @Singular("blueprint")
    private Set<Blueprint> blueprints;

    public static AccountExt fromAccount(Account account) {

        return AccountExt.builder()
                .id(account.getId())
                .name(account.getName())
                .blueprints(account.getBlueprints())
                .build();
    }
}