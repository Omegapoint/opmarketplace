package se.omegapoint.academy.domain.items;

import se.omegapoint.academy.domain.IdentifiedDomainObject;

import java.time.LocalDateTime;
import java.util.UUID;

public final class Item extends IdentifiedDomainObject {
    private final String title;
    private final String description;
    private final int price;
    private final LocalDateTime expires;

    public Item(String title, String description, int price, LocalDateTime expires) {
        super();
        this.title = title;
        this.description = description;
        this.price = price;
        this.expires = expires;
    }

    public Item(UUID id, String title, String description, int price, LocalDateTime expires) {
        super(id);
        this.title = title;
        this.description = description;
        this.price = price;
        this.expires = expires;
    }

    public String title() {
        return title;
    }

    public String description() {
        return description;
    }

    public int price() {
        return price;
    }

    protected LocalDateTime getExpires() {
        return expires;
    }

    public boolean hasExpired(){
        return expires.isAfter(LocalDateTime.now());
    }
}
