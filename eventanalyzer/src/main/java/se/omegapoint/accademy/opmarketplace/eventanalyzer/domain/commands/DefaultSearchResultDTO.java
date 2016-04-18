package se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.commands;

import se.omegapoint.accademy.opmarketplace.eventanalyzer.infrastructure.json_representations.ItemDTO;

public class DefaultSearchResultDTO implements Command {

    public static final String TYPE = "DefaultSearchResult";

    public final int noSeconds;
    public final ItemDTO item;

    public DefaultSearchResultDTO(int noSeconds, ItemDTO item) {
        this.noSeconds = noSeconds;
        this.item = item;
    }

    @Override
    public String type() {
        return TYPE;
    }
}
