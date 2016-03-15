package se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;

import java.sql.Timestamp;

public class RemoteEvent {

    private String type;
    private String data;
    private Timestamp timestamp;

    // TODO: 08/03/16 Fix factory method.
//    public static RemoteEvent fromJsonModel(JsonModel data, String type) {
//
//    }

    public RemoteEvent(JsonModel data, String type){
        this.type = type;
        try {
            this.data = new ObjectMapper().writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    // Empty constructor and getters because of Jackson.
    public RemoteEvent() {}

    public String getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
