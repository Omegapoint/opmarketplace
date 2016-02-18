package se.omegapoint.academy.opmarketplace.marketplace.domain.items.events;

import java.io.Serializable;

public class TitleUpdated implements Serializable {
    private final String newTitle;

    public TitleUpdated(String newTitle) {
        this.newTitle = newTitle;
    }

    public String getNewTitle(){
        return newTitle;
    }
}
