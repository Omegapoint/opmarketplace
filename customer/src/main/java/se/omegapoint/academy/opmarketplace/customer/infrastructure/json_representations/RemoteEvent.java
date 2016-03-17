package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class RemoteEvent {

    private String type;
    private String data;
    // TODO: 17/03/16 Never set on outgoing events
    private Timestamp timestamp;

    // TODO: 08/03/16 Fix factory method.
//    public static RemoteEvent fromJsonModel(JsonModel data, String type) {
//        try {
//            return new RemoteEvent(new ObjectMapper().writeValueAsString(data), type)
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return new RemoteEvent(data, type);
//    }

    public RemoteEvent(JsonModel data, String type) {
        try {
            this.type = notNull(type);
            this.data = new ObjectMapper().writeValueAsString(data);
        } catch (JsonProcessingException e) {
            // TODO: 17/03/16 HJÄLP
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
