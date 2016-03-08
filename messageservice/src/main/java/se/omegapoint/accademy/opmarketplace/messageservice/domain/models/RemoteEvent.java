package se.omegapoint.accademy.opmarketplace.messageservice.domain.models;

import java.sql.Timestamp;

public class RemoteEvent {

    private String type;
    private String data;
    private Timestamp timestamp;

    public RemoteEvent(String type, String data, Timestamp timestamp) {
        this.type = type;
        this.data = data;
        this.timestamp = timestamp;
    }

    public RemoteEvent(){}

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
