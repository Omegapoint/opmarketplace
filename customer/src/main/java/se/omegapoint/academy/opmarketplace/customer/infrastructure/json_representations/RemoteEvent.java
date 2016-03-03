package se.omegapoint.academy.opmarketplace.customer.infrastructure.json_representations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RemoteEvent {

    private String type;
    private String data;

    public RemoteEvent(JsonModel data, String type){
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
