package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoteEvent {

    private String type;
    private String data;

    @JsonCreator
    public RemoteEvent(@JsonProperty("type") String type, @JsonProperty("data") String data) {
        this.type = notNull(type);
        this.data = notNull(data);
    }

    public RemoteEvent(JsonModel data, String type) {
        this.type = type;
        try {
            this.data = new ObjectMapper().writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public String getType() {
        return type;
    }

    public String getData() {
        return data;
    }
}
