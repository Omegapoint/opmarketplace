package se.omegapoint.academy.opmarketplace.customer.infrastructure.event_data_objects;

public class EventData {

    private final String type;
    private final String data;

    public EventData(DataObject data) {
        type = data.getClass().getSimpleName();
        this.data = data.json();
    }

    public EventData(String type, String data) {
        this.type = type;
        this.data = data;
    }

    public String dataObjectType(){
        return type;
    }

    public String data(){
        return data;
    }
}
