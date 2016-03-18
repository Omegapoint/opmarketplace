package se.omegapoint.academy.opmarketplace.customer.domain.events.persistable;


import java.sql.Timestamp;

public interface Persistable {
    Timestamp timestamp();
}
