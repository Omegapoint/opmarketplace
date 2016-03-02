package se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence;

import reactor.bus.Event;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.customer.domain.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AggregateModification;

import java.io.IOException;

public interface AccountRepository extends Consumer<Event<AggregateModification>> {

    Account account(String email) throws IOException;

    @Override
    void accept(Event<AggregateModification> event);

    boolean accountInExistence(String email);
}
