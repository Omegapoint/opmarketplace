package se.omegapoint.academy.opmarketplace.apigateway.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.context.request.async.DeferredResult;
import reactor.bus.Event;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.AccountCreatedModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.AccountModel;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountRequestedListener implements Consumer<Event<AccountModel>> {
    private DeferredResult<AccountModel> result;

    public AccountRequestedListener(DeferredResult<AccountModel> result) {
        this.result = notNull(result);
    }

    @Override
    public void accept(Event<AccountModel> accountEvent) {
        result.setResult(accountEvent.getData());
    }
}
