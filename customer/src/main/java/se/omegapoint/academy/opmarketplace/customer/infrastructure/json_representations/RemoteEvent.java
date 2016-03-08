package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
