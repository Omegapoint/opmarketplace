package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.AccountDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountObtainedDTO implements JsonModel {

    public static final String TYPE = "AccountObtained";

    private AccountDTO account;

    public AccountObtainedDTO() {}

    public AccountDTO getAccount() {
        return account;
    }

}
