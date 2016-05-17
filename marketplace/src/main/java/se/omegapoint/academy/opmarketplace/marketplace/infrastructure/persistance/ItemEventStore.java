package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance;

import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.ItemNotObtained;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.ItemObtained;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.ItemSearchCompleted;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.*;
import se.omegapoint.academy.opmarketplace.marketplace.domain.services.ItemRepository;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Id;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Query;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.factories.ItemFactory;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events.*;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemEventStore implements ItemRepository {

    public static final String ITEM_DOES_NOT_EXIST = "Item does not exist.";

    private final ItemCreatedJPARepository itemCreatedRepository;
    private final ItemChangedJPARepository itemChangedRepository;
    private final ItemOrderJPARepository itemSupplyDeductedRepository;
    private final ItemOrderReverseJPARepository itemOrderReverseRepository;
    private final ItemReservedJPARepository itemReservedRepository;

    public ItemEventStore(ItemCreatedJPARepository itemCreatedRepository,
                          ItemChangedJPARepository itemChangedRepository,
                          ItemOrderJPARepository itemSupplyDeductedRepository,
                          ItemOrderReverseJPARepository itemOrderReverseRepository,
                          ItemReservedJPARepository itemReservedRepository){
        this.itemCreatedRepository = notNull(itemCreatedRepository);
        this.itemChangedRepository = notNull(itemChangedRepository);
        this.itemSupplyDeductedRepository = notNull(itemSupplyDeductedRepository);
        this.itemOrderReverseRepository = notNull(itemOrderReverseRepository);
        this.itemReservedRepository = notNull(itemReservedRepository);
    }

    @Override
    public DomainEvent item(Id id) {
        List<PersistableEvent> events = eventStream(id);
        if (events.isEmpty()){
            return new ItemNotObtained(ITEM_DOES_NOT_EXIST);
        }
        if (itemReservedRepository.findByReservedUntilGreaterThan(new Timestamp(System.currentTimeMillis())).size() > 25) {
            System.out.println("More than 25!");
            ItemFactory.disableReservationsUntil(LocalDateTime.now().plusSeconds(60));
        }
        return new ItemObtained(ItemFactory.fromPersistableEvents(events));
    }

    @Override
    public ItemSearchCompleted search(Query query) {
        HashSet<Id> match = searchCreatedEvents(query);
        match = searchChangedEvents(query, match);

        List<Item> items = match.stream()
                .map(id -> ItemFactory.fromPersistableEvents(eventStream(id)))
                .filter(item -> item.supply().amount() > 0)
                .collect(Collectors.toList());

        return new ItemSearchCompleted(items.stream().filter(item -> itemFilter(item, query)).collect(Collectors.toList()));
    }
    @Override
    public Optional<ItemOrdered> order(Id orderId){
        return retrieveItemOrderedEvent(orderId.toString());
    }

    @Override
    public boolean itemInExistence(Id id) {
        return !itemCreatedRepository.findById(notNull(id).toString()).isEmpty();
    }

    @Override
    public DomainEvent append(PersistableEvent event) {
        notNull(event);
        if (event instanceof ItemCreated){
            add((ItemCreated) event);
        } else if (event instanceof ItemChanged){
            add((ItemChanged) event);
        } else if (event instanceof ItemOrdered){
            add((ItemOrdered) event);
        } else if (event instanceof ItemOrderReversed){
            add((ItemOrderReversed) event);
        } else if (event instanceof ItemReserved) {
            add((ItemReserved) event);
        }
        return event;
    }

    @Override
    public List<ItemReserved> reservationHistorySince(Email user, Timestamp since) {
        return itemReservedRepository.findByReserverIdAndTimeGreaterThan(user.address(), since).stream()
                .map(ItemReservedEntity::domainObject)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ItemOrdered> lastOrderedItem(Email user) {
        return itemSupplyDeductedRepository.findByBuyerIdOrderByTimeDesc(user.address()).stream()
                .findFirst()
                .map(event -> Optional.of(event.domainObject()))
                .orElse(Optional.empty());
    }

    private boolean itemFilter(Item item, Query query){
        for (String term : query.terms()){
            if (item.title().text().toLowerCase().contains(term) || item.description().text().toLowerCase().contains(term)){
                return true;
            }
        }
        return false;
    }

    private List<PersistableEvent> eventStream(Id id){
        List<PersistableEvent> events = new ArrayList<>();
        String itemId = id.toString();
        if (retrieveCreatedEvent(itemId)
                .map(events::add)
                .orElse(false)){
            events.addAll(retrieveChangedEvent(itemId));
            events.addAll(retrieveItemOrderedEvents(itemId));
            events.addAll(retrieveItemOrderReverseEvents(itemId));
            events.addAll(retrieveItemReservedEvents(itemId));
            Collections.sort(events, new PersistableEventComparator());
        }
        return events;
    }

    private HashSet<Id> searchCreatedEvents(Query query){
        HashSet<Id> match = new HashSet<>();
        query.terms().stream()
                .forEach(term -> itemCreatedRepository.findByTitleContainingOrDescriptionContainingAllIgnoreCase(term, term).stream()
                        .forEach(itemCreatedEntity -> match.add(itemCreatedEntity.domainObject().itemId())));
        return match;
    }

    private HashSet<Id> searchChangedEvents(Query query, HashSet<Id> match) {
        query.terms().stream()
                .forEach(term -> itemChangedRepository.findByTitleContainingOrDescriptionContainingAllIgnoreCase(term, term).stream()
                        .forEach(itemChangedEntity -> match.add(itemChangedEntity.domainObject().itemId())));
        return match;
    }

    private Optional<ItemCreated> retrieveCreatedEvent(String id) {
        return itemCreatedRepository.findById(notNull(id)).stream()
                .map(ItemCreatedEntity::domainObject).findAny();
    }

    private List<ItemChanged> retrieveChangedEvent(String id) {
        return itemChangedRepository.findById(id).stream()
                .map(ItemChangedEntity::domainObject).collect(Collectors.toList());
    }

    private Optional<ItemOrdered> retrieveItemOrderedEvent(String orderId) {
        return itemSupplyDeductedRepository.findByOrderId(orderId).stream()
                .map(ItemOrderEntity::domainObject).findAny();
    }

    private List<ItemOrdered> retrieveItemOrderedEvents(String itemId) {
        return itemSupplyDeductedRepository.findById(itemId).stream()
                .map(ItemOrderEntity::domainObject).collect(Collectors.toList());
    }

    private List<ItemOrderReversed> retrieveItemOrderReverseEvents(String itemId) {
        return itemOrderReverseRepository.findByItemId(itemId).stream()
                .map(ItemOrderReversedEntity::domainObject).collect(Collectors.toList());
    }

    private List<ItemReserved> retrieveItemReservedEvents(String itemId) {
        return itemReservedRepository.findByItemId(itemId).stream()
                .map(ItemReservedEntity::domainObject).collect(Collectors.toList());
    }

    private void add(ItemCreated itemCreated){
        notNull(itemCreated);
        itemCreatedRepository.save(new ItemCreatedEntity(
                itemCreated.item().id().toString(),
                itemCreated.item().title().text(),
                itemCreated.item().description().text(),
                itemCreated.item().price().amount(),
                itemCreated.item().supply().amount(),
                itemCreated.item().seller().address(),
                itemCreated.timestamp()));
    }

    private void add(ItemChanged itemChanged){
        notNull(itemChanged);
        itemChangedRepository.save(new ItemChangedEntity(
                itemChanged.item().id().toString(),
                itemChanged.item().title().text(),
                itemChanged.item().description().text(),
                itemChanged.item().price().amount(),
                itemChanged.item().supply().amount(),
                itemChanged.item().seller().address(),
                itemChanged.timestamp()));
    }

    private void add(ItemOrdered itemOrdered) {
        itemSupplyDeductedRepository.save(new ItemOrderEntity(notNull(itemOrdered)));
    }

    private void add(ItemOrderReversed itemOrderReversed) {
        notNull(itemOrderReversed);
        itemOrderReverseRepository.save(new ItemOrderReversedEntity(
                itemOrderReversed.originalOrderId().toString(),
                itemOrderReversed.itemId().toString(),
                itemOrderReversed.quantity().amount(),
                itemOrderReversed.timestamp()));
    }

    private void add(ItemReserved itemReserved) {
        itemReservedRepository.save(new ItemReservedEntity(notNull(itemReserved)));
    }
}
