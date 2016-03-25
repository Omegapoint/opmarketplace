package se.omegapoint.academy.opmarketplace.marketplace.application;

import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemCreationRequested;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.ItemNotCreated;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.PersistableEvent;
import se.omegapoint.academy.opmarketplace.marketplace.domain.services.EventPublisher;
import se.omegapoint.academy.opmarketplace.marketplace.domain.services.ItemRepository;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.external_events.ItemCreationRequestedDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemService {

    private final ItemRepository repository;
    private final EventPublisher publisher;

    public ItemService(ItemRepository repository, EventPublisher publisher) {
        this.repository = notNull(repository);
        this.publisher = notNull(publisher);
    }

    public void itemCreationRequested(ItemCreationRequestedDTO dto){
        DomainEvent event = DomainObjectResult.of(ItemCreationRequestedDTO::domainObject, notNull(dto))
                .map(this::createItem)
                .orElseReason(ItemNotCreated::new);
        publisher.publish(event, dto.requestId());
    }

    private DomainEvent createItem(ItemCreationRequested request) {
        PersistableEvent persistableEvent = Item.createItem(request);
        repository.append(persistableEvent);
        return persistableEvent;
    }
}