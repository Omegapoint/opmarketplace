package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;

import static se.sawano.java.commons.lang.validate.Validate.notNull;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemCreatedDTO implements Event {

    public static final String TYPE = "ItemCreated";

    public final String requestId;

    public ItemCreatedDTO(@JsonProperty("requestId") String requestId){
        this.requestId = notNull(requestId);
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
