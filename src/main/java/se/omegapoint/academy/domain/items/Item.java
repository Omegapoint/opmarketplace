package se.omegapoint.academy.domain.items;

import se.omegapoint.academy.domain.IdentifiedDomainObject;

import java.time.LocalDateTime;
import java.util.UUID;

public final class Item extends IdentifiedDomainObject {
    private final Title title;
    private final Description description;
    private final Price price;
    private final LocalDateTime expires;

    public Item(String title, String description, String price, LocalDateTime expires) {
        super();
        this.title = new Title(title);
        this.description = new Description(description);
        this.price = new Price(price);
        this.expires = expires;
    }

    public Item(UUID id, String title, String description, String price, LocalDateTime expires) {
        super(id);
        this.title = new Title(title);
        this.description = new Description(description);
        this.price = new Price(price);
        this.expires = expires;
    }

    public Title title() {
        return title;
    }

    public Description description() {
        return description;
    }

    public Price price() {
        return price;
    }

    protected LocalDateTime getExpires() {
        return expires;
    }

    public boolean hasExpired(){
        return expires.isAfter(LocalDateTime.now());
    }
}
