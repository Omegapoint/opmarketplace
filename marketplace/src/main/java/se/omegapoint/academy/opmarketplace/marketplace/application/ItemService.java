package se.omegapoint.academy.opmarketplace.marketplace.application;

import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemPaymentNotCompleted;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.*;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemOrderReversed;
import se.omegapoint.academy.opmarketplace.marketplace.domain.services.EventPublisher;
import se.omegapoint.academy.opmarketplace.marketplace.domain.services.ItemRepository;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.external_events.*;

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
        } else if (dto instanceof ItemChangeRequestedDTO) {
            handle((ItemChangeRequestedDTO) dto);
        } else if (dto instanceof ItemPurchaseRequestedDTO) {
            handle((ItemPurchaseRequestedDTO) dto);
        } else if (dto instanceof ItemPaymentNotCompletedDTO) {
            handle((ItemPaymentNotCompletedDTO) dto);
        } else {
            System.err.println("ItemService: Did not recognize event received from reactor bus.");
        }
    }

    private void handle(ItemPurchaseRequestedDTO dto) {
        DomainEvent event = DomainObjectResult.of(ItemPurchaseRequestedDTO::domainObject, notNull(dto))
                .map(request -> repository.item(request.itemId()))
                .orElseReason(ItemNotOrdered::new);

        if (event instanceof ItemObtained){
            event = DomainObjectResult.of(item -> item.handle(dto.domainObject()), ((ItemObtained) event).item())
                    .map(repository::append)
                    .orElseReason(ItemNotOrdered::new);
        }
        publisher.publish(event, dto.requestId());
    }

    private void handle(ItemCreationRequestedDTO dto){
        DomainEvent event = DomainObjectResult.of(ItemCreationRequestedDTO::domainObject, notNull(dto))
                .map(request -> repository.append(Item.createItem(request)))
                .orElseReason(ItemNotCreated::new);
        publisher.publish(event, dto.requestId());
    }

    private void handle(ItemRequestedDTO dto){
        DomainEvent event = DomainObjectResult.of(ItemRequestedDTO::domainObject, notNull(dto))
                .map(request -> repository.item(request.itemId()))
                .orElseReason(ItemNotCreated::new);
        publisher.publish(event, dto.requestId());
    }

    private void handle(ItemSearchRequestedDTO dto) {
        DomainEvent event = DomainObjectResult.of(ItemSearchRequestedDTO::domainObject, notNull(dto))
                .map(request -> repository.search(request.query()))
                .orElseReason(ItemsNotSearched::new);
        publisher.publish(event, dto.requestId());
    }

    private void handle(ItemChangeRequestedDTO dto) {
        DomainEvent event = DomainObjectResult.of(ItemChangeRequestedDTO::domainObject, notNull(dto))
                .map(request -> repository.item(request.itemId()))
                .orElseReason(ItemNotChanged::new);

        if (event instanceof ItemObtained){
            event = DomainObjectResult.of(item -> item.handle(dto.domainObject()), ((ItemObtained) event).item())
                .map(repository::append)
                .orElseReason(ItemNotChanged::new);
        }
        publisher.publish(event, dto.requestId());
    }

    private void handle(ItemPaymentNotCompletedDTO dto) {
        DomainObjectResult.of(ItemPaymentNotCompletedDTO::domainObject, notNull(dto))
                .map(request -> repository.order(request.orderId())
                    .map(itemOrdered -> repository.append(new ItemOrderReversed(
                            itemOrdered.order().id(),
                            itemOrdered.order().itemId(),
                            itemOrdered.order().quantity()))));
    }
}
