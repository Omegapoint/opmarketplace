package se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.internal_events;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.ItemSearchCompleted;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.Serializer;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.dto.domain_object.ItemDTO;

import java.util.List;
import java.util.stream.Collectors;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemSearchCompletedDTO implements Event, Serializer {

    public static final String TYPE = "ItemSearchCompleted";

    public final String requestId;
    public final List<ItemDTO> items;

    public ItemSearchCompletedDTO(ItemSearchCompleted event, String requestId) {
        this.requestId = notNull(requestId);
        this.items = notNull(event).items().stream()
                .map(ItemDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public String requestId() {
        return requestId;
    }
}
