package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance;

import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.ItemNotObtained;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.ItemObtained;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.ItemSearchCompleted;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.*;
import se.omegapoint.academy.opmarketplace.marketplace.domain.services.ItemRepository;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.factories.ItemFactory;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events.ItemChangedEntity;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events.ItemCreatedEntity;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events.ItemOrderEntity;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events.ItemOrderReversedEntity;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories.ItemChangedJPARepository;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories.ItemCreatedJPARepository;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories.ItemOrderJPARepository;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories.ItemOrderReverseJPARepository;

import java.util.*;
import java.util.stream.Collectors;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemEventStore implements ItemRepository {

    public static final String ITEM_DOES_NOT_EXIST = "Item does not exist.";

    private final ItemCreatedJPARepository itemCreatedRepository;
    private final ItemChangedJPARepository itemChangedRepository;
    private final ItemOrderJPARepository itemSupplyDeductedRepository;
    private final ItemOrderReverseJPARepository itemOrderReverseRepository;

    public ItemEventStore(ItemCreatedJPARepository itemCreatedRepository,
                          ItemChangedJPARepository itemChangedRepository,
                          ItemOrderJPARepository itemSupplyDeductedRepository,
                          ItemOrderReverseJPARepository itemOrderReverseRepository){
        this.itemCreatedRepository = notNull(itemCreatedRepository);
        this.itemChangedRepository = notNull(itemChangedRepository);
        this.itemSupplyDeductedRepository = notNull(itemSupplyDeductedRepository);
        this.itemOrderReverseRepository = itemOrderReverseRepository;
    }

    @Override
    public DomainEvent item(UUID id) {
        String itemId = notNull(id).toString();

        List<PersistableEvent> events = new ArrayList<>();
        retrieveCreatedEvent(itemId)
                .map(events::add)
                .orElse(false);

        if (events.isEmpty()){
            return new ItemNotObtained(ITEM_DOES_NOT_EXIST);
        }

        events.addAll(retrieveChangedEvent(itemId));
        events.addAll(retrieveItemOrderedEvents(itemId));
        events.addAll(retrieveItemOrderReverseEvents(itemId));
        Collections.sort(events, new PersistableEventComparator());

        return new ItemObtained(ItemFactory.fromPersistableEvents(events));
    }

    @Override
    public ItemSearchCompleted search(String query) {
        HashMap<UUID, List<PersistableEvent>> matches = new HashMap<>();

        searchCreatedEvents(query).stream().forEach(itemCreated -> {
            if (!matches.containsKey(itemCreated.itemId())){
                matches.put(itemCreated.itemId(), new ArrayList<>());
            }
            matches.get(itemCreated.itemId()).add(itemCreated);
        });

        searchChangedEvents(query).stream().forEach(itemChanged -> {
            if (!matches.containsKey(itemChanged.itemId())){
                matches.put(itemChanged.itemId(), new ArrayList<>());
            }
            matches.get(itemChanged.itemId()).add(itemChanged);
        });

        List<Item> items = new ArrayList<>();
        for (UUID id : matches.keySet()){
            matches.get(id).addAll(retrieveItemOrderedEvents(id.toString()));
            matches.get(id).addAll(retrieveItemOrderReverseEvents(id.toString()));
            Collections.sort(matches.get(id), new PersistableEventComparator());
            items.add(ItemFactory.fromPersistableEvents(matches.get(id)));
        }
        return new ItemSearchCompleted(items);
    }
    @Override
    public Optional<ItemOrdered> order(UUID orderId){
        return retrieveItemOrderedEvent(orderId.toString());
    }

    @Override
    public boolean itemInExistence(UUID id) {
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
        }
        return event;
    }

    private List<ItemCreated> searchCreatedEvents(String query){
        return itemCreatedRepository.findByTitleContainingOrDescriptionContainingAllIgnoreCase(query, query).stream()
                .map(ItemCreatedEntity::domainObject).collect(Collectors.toList());
    }

    private List<ItemChanged> searchChangedEvents(String query) {
        return itemChangedRepository.findByTitleContainingOrDescriptionContainingAllIgnoreCase(query, query).stream()
                .map(ItemChangedEntity::domainObject).collect(Collectors.toList());
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
}
