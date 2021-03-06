package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;

import static se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.StringRandomizer.randomString;
import static se.sawano.java.commons.lang.validate.Validate.notBlank;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemChangeRequestedDTO implements Event{

    public static final String TYPE = "ItemChangeRequested";

    public final String requestId;
    public final String id;
    public final String title;
    public final String description;
    public final int price;
    public final int supply;

    @JsonCreator
    public ItemChangeRequestedDTO(
            @JsonProperty("id") String itemId,
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("price") int price,
            @JsonProperty("supply") int supply) {
        this.requestId = randomString();
        this.id = notBlank(itemId);
        this.title = notNull(title);
        this.description = notNull(description);
        this.price = notNull(price);
        this.supply = notNull(supply);
    }

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public String requestId() {
        return requestId;
    }
}
