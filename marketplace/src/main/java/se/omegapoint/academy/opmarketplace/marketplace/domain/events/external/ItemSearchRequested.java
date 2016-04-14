package se.omegapoint.academy.opmarketplace.marketplace.domain.events.external;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Query;

import static se.sawano.java.commons.lang.validate.Validate.notBlank;

public class ItemSearchRequested implements DomainEvent {

    private final Query query;

    public ItemSearchRequested(String query) {
        this.query = new Query(query);
    }

    public Query query(){
        return this.query;
    }
}
