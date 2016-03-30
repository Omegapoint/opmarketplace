package se.omegapoint.academy.opmarketplace.marketplace.domain.events.external;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.DomainEvent;

import static se.sawano.java.commons.lang.validate.Validate.notBlank;

public class ItemSearchRequested implements DomainEvent {

    private final String query;

    public ItemSearchRequested(String query) {
        this.query = notBlank(query);
    }

    public String query(){
        return this.query;
    }
}
