package se.omegapoint.academy.domain;

import java.util.UUID;

public abstract class IdentifiedDomainObject {

    private UUID id;

    public IdentifiedDomainObject(){
        this.id = UUID.randomUUID();
    }

    protected IdentifiedDomainObject(UUID id){
        this.id = id;
    }

    public String id(){
        return id.toString();
    }
}
