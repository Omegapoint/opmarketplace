package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountObtainedModel implements JsonModel{

    public static final String TYPE = "AccountObtained";

    private AccountModel account;

    public AccountObtainedModel() {}

    public AccountModel getAccount() {
        return account;
    }

}
