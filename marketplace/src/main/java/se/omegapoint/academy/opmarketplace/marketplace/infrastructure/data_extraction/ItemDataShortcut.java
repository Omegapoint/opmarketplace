package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.data_extraction;

import org.springframework.beans.factory.annotation.Autowired;
import se.omegapoint.academy.opmarketplace.marketplace.domain.entities.Item;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.ItemObtained;
import se.omegapoint.academy.opmarketplace.marketplace.domain.services.ItemRepository;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.events.ItemOrderEntity;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.persistance.jpa_repositories.ItemOrderJPARepository;

import java.sql.Timestamp;
import java.util.Optional;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class ItemDataShortcut {

    @Autowired
    ItemOrderJPARepository itemOrderJPARepository;

    @Autowired
    ItemRepository itemRepository;

    protected Optional<Item> getMostPopularItemSince(Timestamp time) {

        return itemOrderJPARepository.findByTimeGreaterThan(time).stream()
                .map(ItemOrderEntity::domainObject)
                .collect(groupingBy(event -> event.order().itemId(), counting()))
                .entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .map(e -> ((ItemObtained) itemRepository.item(e.getKey())).item());
    }
}
