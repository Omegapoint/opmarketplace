package se.omegapoint.academy.opmarketplace.marketplace.application;

import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.ItemNotCreated;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.ItemsNotSearched;
import se.omegapoint.academy.opmarketplace.marketplace.domain.services.EventPublisher;
import se.omegapoint.academy.opmarketplace.marketplace.domain.services.ItemRepository;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.external_events.ItemCreationRequestedDTO;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.external_events.ItemRequestedDTO;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.external_events.ItemSearchRequestedDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemService implements Consumer<reactor.bus.Event<Event>> {

    private final ItemRepository repository;
    private final EventPublisher publisher;

    public ItemService(ItemRepository repository, EventPublisher publisher) {
        this.repository = notNull(repository);
        this.publisher = notNull(publisher);
    }

    @Override
    public void accept(reactor.bus.Event<Event> event) {
        Event dto = notNull(notNull(event).getData());
        if (dto instanceof ItemCreationRequestedDTO) {
            handle((ItemCreationRequestedDTO) dto);
        } else if (dto instanceof ItemRequestedDTO) {
            handle((ItemRequestedDTO) dto);
        } else if (dto instanceof ItemSearchRequestedDTO) {
            handle((ItemSearchRequestedDTO) dto);
        }
    }

    private void handle(ItemCreationRequestedDTO dto){
        DomainEvent event = DomainObjectResult.of(ItemCreationRequestedDTO::domainObject, notNull(dto))
                .map(request -> repository.append(Item.createItem(request)))
                .orElseReason(ItemNotCreated::new);
        publisher.publish(event, dto.requestId());
    }

    private void handle(ItemRequestedDTO dto){
        DomainEvent event = DomainObjectResult.of(ItemRequestedDTO::domainObject, notNull(dto))
                .map(request -> repository.item(request.itemID()))
                .orElseReason(ItemNotCreated::new);
        publisher.publish(event, dto.requestId());
    }


    private void handle(ItemSearchRequestedDTO dto) {
        DomainEvent event = DomainObjectResult.of(ItemSearchRequestedDTO::domainObject, notNull(dto))
                .map(request -> repository.search(request.query()))
                .orElseReason(ItemsNotSearched::new);
        publisher.publish(event, dto.requestId());
    }
}
