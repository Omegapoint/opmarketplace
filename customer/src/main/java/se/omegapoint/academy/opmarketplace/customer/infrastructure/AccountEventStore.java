package se.omegapoint.academy.opmarketplace.customer.infrastructure;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.customer.domain.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.domain.services.AccountEventPublisherService;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance.AccountEventJPARepository;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance.DomainEventEntity;

import java.io.IOException;
import java.util.stream.Collectors;

public class AccountEventStore implements Consumer<Event<DomainEvent>> {

    private AccountEventJPARepository repository;
    private EventBus eventBus;

    public AccountEventStore(EventBus eventBus, AccountEventJPARepository repository){
        this.repository = repository;
        this.eventBus = eventBus;
        this.eventBus.on(Selectors.object("AccountCreated"), this);
        this.eventBus.on(Selectors.object("AccountUserChanged"), this);
    }

    public Account account(String email) throws IOException {
        return new Account(repository.findByAggregateIdOrderByTime(email).stream()
                .map(DomainEvent::new)
                .collect(Collectors.toList()), new AccountEventPublisherService(eventBus));
    }


    @Override
    public void accept(Event<DomainEvent> event) {
        DomainEvent domainEvent = event.getData();
        if(domainEvent.aggregateName().equals(Account.class.getSimpleName())){
            repository.save(new DomainEventEntity(domainEvent.aggregateId(), domainEvent.aggregateName(), domainEvent.eventType(), domainEvent.eventData(), domainEvent.time()));
        }
    }
}
