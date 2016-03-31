package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.item.ItemDTO;

import java.util.List;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemSearchCompletedDTO implements Event {

    public static final String TYPE = "ItemSearchCompleted";

    @JsonIgnore
    public final String requestId;
    public final List<ItemDTO> items;

    @JsonCreator
    public ItemSearchCompletedDTO(@JsonProperty("requestId") String requestId,
                                  @JsonProperty("items") List<ItemDTO> items) {
        this.requestId = notNull(requestId);
        this.items = notNull(items);
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
