package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.AccountModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountObtainedModel implements JsonModel {

    public static final String TYPE = "AccountObtained";

    private AccountModel account;

    public AccountObtainedModel() {}

    public AccountObtainedModel(AccountModel account) {
        this.account = account;
    }

    public AccountModel getAccount() {
        return account;
    }

}
