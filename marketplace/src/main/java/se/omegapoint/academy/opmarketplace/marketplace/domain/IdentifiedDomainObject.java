package se.omegapoint.academy.opmarketplace.marketplace.domain;

import java.util.UUID;

public abstract class IdentifiedDomainObject {

    private UUID id;

    protected IdentifiedDomainObject(UUID id){
        this.id = id;
    }

    public UUID id(){
        return id;
    }
}
