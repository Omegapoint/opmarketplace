package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance;

import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.services.ItemRepository;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.PersistableEvent;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories.ItemCreatedJPARepository;

import java.util.Optional;

public class ItemRepositoryDomain implements ItemRepository {

    private final ItemCreatedJPARepository itemCreatedRepository;

    public ItemRepositoryDomain(ItemCreatedJPARepository itemCreatedRepository){
        this.itemCreatedRepository = itemCreatedRepository;
    }


    @Override
    public Optional<Item> item(String id) {
        return Optional.empty();
    }

    @Override
    public boolean itemInExistence(String id) {
        return false;
    }

    @Override
    public void append(PersistableEvent event) {

    }
}
