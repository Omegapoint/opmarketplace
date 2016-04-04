package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.item.ItemDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemChangedDTO  implements Event {

    public static final String TYPE = "ItemChanged";

    @JsonIgnore
    public final String requestId;
    public final ItemDTO item;

    public ItemChangedDTO(@JsonProperty("requestId") String requestId,
                          @JsonProperty("item") ItemDTO item) {
        this.requestId = requestId;
        this.item = notNull(item);
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
