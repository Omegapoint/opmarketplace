package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance;

import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemCreated;
import se.omegapoint.academy.opmarketplace.marketplace.domain.services.ItemRepository;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.PersistableEvent;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.factories.ItemFactory;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events.ItemCreatedEntity;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories.ItemCreatedJPARepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemEventStore implements ItemRepository {

    private final ItemCreatedJPARepository itemCreatedRepository;

    public ItemEventStore(ItemCreatedJPARepository itemCreatedRepository){
        this.itemCreatedRepository = itemCreatedRepository;
    }

    @Override
    public Optional<Item> item(String id) {
        notNull(id);

        List<PersistableEvent> events = new ArrayList<>();
        retrieveCreatedEvent(id)
                .map(events::add)
                .orElse(false);

        return Optional.of(ItemFactory.fromPersistableEvents(events));
    }

    private Optional<ItemCreated> retrieveCreatedEvent(String id) {
        return itemCreatedRepository.findById(notNull(id)).stream()
                .map(ItemCreatedEntity::domainEvent).findAny();
    }

    @Override
    public boolean itemInExistence(String id) {
        return !itemCreatedRepository.findById(notNull(id)).isEmpty();
    }

    @Override
    public void append(PersistableEvent event) {
        if (notNull(event) instanceof ItemCreated){
            add((ItemCreated) event);
        }
    }

    private void add(ItemCreated itemCreated){
        notNull(itemCreated);
        itemCreatedRepository.save(new ItemCreatedEntity(itemCreated.item().id(),
                itemCreated.item().title().text(),
                itemCreated.item().description().text(),
                itemCreated.item().price().amount(),
                itemCreated.item().expiration().time()));
    }
}
