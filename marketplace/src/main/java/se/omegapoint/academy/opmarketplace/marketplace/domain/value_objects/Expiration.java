package se.omegapoint.academy.opmarketplace.marketplace.domain.value_objects;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;


public class Expiration {
    private final Timestamp time;

    public Expiration(Timestamp time) {
        this.time = notNull(time);
    }

    public Timestamp time(){
        return this.time;
    }

    public boolean hasExpired(){
        return new Timestamp(System.currentTimeMillis()).after(time);
    }
}
