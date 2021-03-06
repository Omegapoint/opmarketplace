package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;

import static se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.StringRandomizer.randomString;
import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemCreationRequestedDTO implements Event {

    public static final String TYPE = "ItemCreationRequested";

    public final String requestId;
    public final String title;
    public final String description;
    public final int price;
    public final int supply;
    public final String seller;

    @JsonCreator
    public ItemCreationRequestedDTO(
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("price") int price,
            @JsonProperty("supply") int supply,
            @JsonProperty("seller") String seller) {
        this.requestId = randomString();
        this.title = notNull(title) ;
        this.description = notNull(description);
        this.price = notNull(price);
        this.supply = notNull(supply);
        this.seller = notNull(seller);
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
