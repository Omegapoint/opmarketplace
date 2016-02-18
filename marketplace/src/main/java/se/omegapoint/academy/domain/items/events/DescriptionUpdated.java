package se.omegapoint.academy.domain.items.events;

import java.io.Serializable;

public class DescriptionUpdated implements Serializable{

    private final String newDescription;

    public DescriptionUpdated(String newDescription) {
        this.newDescription = newDescription;
    }

    public String getNewDescription(){
        return newDescription;
    }
}
