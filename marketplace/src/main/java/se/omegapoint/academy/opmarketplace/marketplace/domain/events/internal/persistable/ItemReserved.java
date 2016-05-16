package se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable;

import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Email;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Id;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.Quantity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class ItemReserved implements PersistableEvent {
    private final Id itemId;
    private Quantity quantity;
    private Email reserver;
    private final Timestamp reservedUntil;
    private final Timestamp timestamp;

    public ItemReserved(Id itemId, Quantity quantity, Email reserver) {
        this(itemId, quantity, reserver, Timestamp.valueOf(LocalDateTime.now().plusSeconds(10)), new Timestamp(System.currentTimeMillis()));
    }

    public ItemReserved(Id itemId, Quantity quantity, Email reserver, Timestamp reservedUntil, Timestamp timestamp) {
        isTrue(notNull(timestamp).before(new Timestamp(System.currentTimeMillis() + 1)));
        this.itemId = notNull(itemId);
        this.reservedUntil = notNull(reservedUntil);
        this.quantity = notNull(quantity);
        this.reserver = notNull(reserver);
        this.timestamp = notNull(timestamp);
    }

    public Id itemId() {
        return itemId;
    }

    public Quantity quantity() {
        return quantity;
    }

    public Email reserver() {
        return reserver;
    }

    public Timestamp reservedUntil() {
        return reservedUntil;
    }

    @Override
    public Timestamp timestamp() {
        return timestamp;
    }
}
