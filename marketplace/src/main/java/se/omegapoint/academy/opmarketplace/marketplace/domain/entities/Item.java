package se.omegapoint.academy.opmarketplace.marketplace.domain.entities;

import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemChangeRequested;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemCreationRequested;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemPurchaseRequested;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.external.ItemReservationRequested;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemChanged;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemCreated;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemOrdered;
import se.omegapoint.academy.opmarketplace.marketplace.domain.events.internal.persistable.ItemReserved;
import se.omegapoint.academy.opmarketplace.marketplace.domain.services.ItemRepository;
import se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects.*;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static se.sawano.java.commons.lang.validate.Validate.isTrue;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

public final class Item {
    private final Id id;
    private final Title title;
    private final Description description;
    private final Credit price;
    private final Quantity supply;
    private final Email seller;

    public Item(Id id, Title title, Description description, Credit price, Quantity supply, Email seller) {
        this.id = notNull(id);
        this.title = notNull(title);
        this.description = notNull(description);
        this.price = notNull(price);
        this.supply = notNull(supply);
        this.seller = notNull(seller);
    }

    public Id id(){
        return this.id;
    }

    public Title title() {
        return title;
    }

    public Description description() {
        return description;
    }

    public Credit price() {
        return price;
    }

    public Quantity supply(){
        return supply;
    }

    public Email seller(){
        return seller;
    }

    public static ItemCreated createItem(ItemCreationRequested request){
        notNull(request);
        return new ItemCreated(new Item(new Id(),
                request.title(),
                request.description(),
                request.price(),
                request.supply(),
                request.seller()));
    }

    public ItemChanged handle(ItemChangeRequested request){
        isTrue(notNull(request).itemId().equals(id()));
        return new ItemChanged(new Item(request.itemId(),
                request.title(),
                request.description(),
                request.price(),
                request.supply(),
                this.seller));
    }

    public ItemOrdered handle(ItemPurchaseRequested request){
        isTrue(notNull(request).itemId().equals(id()));
        try {
            this.supply().remove(request.quantity());
        } catch (IllegalArgumentValidationException e){
            throw new IllegalArgumentValidationException("Insufficient supply.");
        }
        return new ItemOrdered(new Order(request.itemId(),
                seller(),
                request.quantity(),
                new Credit(price().amount() * request.quantity().amount()),
                request.buyer()));
    }

    public ItemReserved handle(ItemReservationRequested request, ItemRepository itemRepository) {
        isTrue(notNull(request).itemId().equals(id));
        if (request.quantity().amount() <= supply.amount()) {
            Timestamp lastOrderByUser = itemRepository.lastOrderedItem(request.reserver())
                    .map(ItemOrdered::timestamp)
                    .orElse(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));

            Timestamp lastOrder = itemRepository.lastOrderedItem(request.itemId())
                    .map(ItemOrdered::timestamp)
                    .orElse(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));

            boolean tooManyReservationsByUser = itemRepository.expiredReservationsSince(request.reserver(), lastOrderByUser).size() >= 10;
            boolean tooManyReservations = itemRepository.expiredReservationsSince(request.itemId(), lastOrder).size() >= 3;

            if (tooManyReservationsByUser) {
                throw new IllegalArgumentException("This account has too many reservations without purchase. Please contact customer service");
            } else if (tooManyReservations) {
                throw new IllegalArgumentException("This item has had too many reservations in the latest taiiiiim...");
            } else {
                return new ItemReserved(id, request.quantity(), request.reserver());
            }
        } else {
            throw new IllegalArgumentException("Insufficient supply.");
        }
    }
}
