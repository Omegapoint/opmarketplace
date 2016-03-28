package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.outgoing.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.Event;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.item.DescriptionDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.item.PriceDTO;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.item.TitleDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemCreationRequestedDTO implements Event {

    public static final String TYPE = "ItemCreationRequested";
    public final String requestId;
    public final TitleDTO title;
    public final DescriptionDTO description;
    public final PriceDTO price;

    @JsonCreator
    public ItemCreationRequestedDTO(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("title") TitleDTO title,
            @JsonProperty("description") DescriptionDTO description,
            @JsonProperty("price") PriceDTO price) {
        this.requestId = notNull(requestId);
        this.title = notNull(title);
        this.description = notNull(description);
        this.price = notNull(price);
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
