package se.omegapoint.accademy.opmarketplace.messageservice.infrastructure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventMetaData {
    public final String type;
    @JsonCreator
    public EventMetaData(@JsonProperty("type") String type) {
        this.type = notNull(type);
    }
}
