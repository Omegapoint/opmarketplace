package se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.customer.domain.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountCreated;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AccountUserChanged;
import se.omegapoint.academy.opmarketplace.customer.domain.events.AggregateModification;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_data_objects.AccountCreatedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_data_objects.AccountUserChangedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance.AccountUserChangedJPA;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_persistance.AccountCreatedJPA;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_publishing.AccountEventPublisherService;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AccountEventStore implements Consumer<Event<AggregateModification>> {

    private AccountCreatedJPA createAccountRepository;
    private AccountUserChangedJPA userChangedRepository;

    private EventBus eventBus;

    public AccountEventStore(EventBus eventBus, AccountCreatedJPA createAccountRepository, AccountUserChangedJPA userChangedRepository){
        this.createAccountRepository = createAccountRepository;
        this.userChangedRepository = userChangedRepository;
        this.eventBus = eventBus;
        this.eventBus.on(Selectors.regex("Account\\w*"), this);
    }

    public Account account(String email) throws IOException {
        List<DomainEvent> domainEvents = new ArrayList<>();
        domainEvents.addAll(createAccountRepository.findByAggregateMemberIdOrderByTime(email).stream()
                .map(AccountCreatedModel::domainEvent)
                .collect(Collectors.toList()));
        domainEvents.addAll(userChangedRepository.findByAggregateMemberIdOrderByTime(email).stream()
                .map(AccountUserChangedModel::domainEvent)
                .collect(Collectors.toList()));
        Collections.sort(domainEvents);
        return new Account(domainEvents, new AccountEventPublisherService(eventBus));
    }


    @Override
    public void accept(Event<AggregateModification> event) {
        AggregateModification domainEvent = event.getData();
        if (domainEvent instanceof AccountCreated){
            createAccountRepository.save(new AccountCreatedModel((AccountCreated)domainEvent));
        }
        else if (domainEvent instanceof AccountUserChanged){
            userChangedRepository.save(new AccountUserChangedModel((AccountUserChanged)domainEvent));
        }
    }

    public boolean accountInExistence(String email) {
        return createAccountRepository.findByAggregateMemberIdOrderByTime(email).size() > 0;
    }

    private List<DomainEvent> mergeDomainEvents(List<List<DomainEvent>> lists){
        ArrayList<DomainEvent> result = new ArrayList<>();
        Timestamp min = new Timestamp(System.currentTimeMillis());
        int listNr = 0;
        int[] index = new int[lists.size()];
        Arrays.fill(index, 0);
        boolean changed = true;

        while(changed) {
            changed = false;
            for (int i = 0; i < lists.size(); i++) {
                if (index[i] < lists.get(i).size() && lists.get(i).get(index[i]).time().before(min)) {
                    listNr = i;
                    min = lists.get(i).get(index[i]).time();
                    changed = true;
                }
            }
            if (changed)
                result.add(lists.get(listNr).get(index[listNr]++));
            min = new Timestamp(System.currentTimeMillis());
        }
        return result;
    }
}
