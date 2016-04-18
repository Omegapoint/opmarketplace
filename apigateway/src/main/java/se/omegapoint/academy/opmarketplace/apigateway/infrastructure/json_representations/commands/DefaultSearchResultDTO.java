package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.item.ItemDTO;

public class DefaultSearchResultDTO {
    public static final String TYPE = "DefaultSearchResult";

    public final int noSeconds;
    public final ItemDTO item;

    @JsonCreator
    public DefaultSearchResultDTO(@JsonProperty("noSeconds") int noSeconds, @JsonProperty("item") ItemDTO item) {
        this.noSeconds = noSeconds;
        this.item = item;
    }
}
