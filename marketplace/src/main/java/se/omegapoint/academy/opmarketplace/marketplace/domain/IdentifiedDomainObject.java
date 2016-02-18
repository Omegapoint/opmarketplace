package se.omegapoint.academy.opmarketplace.marketplace.domain;

import java.util.UUID;

public abstract class IdentifiedDomainObject {

    private UUID id;

    public IdentifiedDomainObject(){
        this.id = UUID.randomUUID();
    }

    protected IdentifiedDomainObject(String id){
        this.id = UUID.fromString(id);
    }

    protected IdentifiedDomainObject(UUID id){
        this.id = id;
    }

    public String id(){
        return id.toString();
    }
}
