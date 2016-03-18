package se.omegapoint.academy.opmarketplace.apigateway.infrastructure;


import com.fasterxml.jackson.databind.ObjectMapper;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.*;

import java.io.IOException;

public class TestPublisher implements RemoteEventPublisher {
    private ObjectMapper json = new ObjectMapper();
    private JsonModel lastestEvent;
    @Override
    public void publish(RemoteEvent remoteEvent) {
        try {
            switch (remoteEvent.getType()) {
                case AccountCreationRequestedDTO.TYPE:
                    lastestEvent = json.readValue(remoteEvent.getData(), AccountCreationRequestedDTO.class);
                    break;
                case AccountRequestedDTO.TYPE:
                    lastestEvent = json.readValue(remoteEvent.getData(), AccountRequestedDTO.class);
                    break;
                case AccountUserChangeRequestedDTO.TYPE:
                    lastestEvent = json.readValue(remoteEvent.getData(), AccountUserChangeRequestedDTO.class);
                    break;
                case AccountDeletionRequestedDTO.TYPE:
                    lastestEvent = json.readValue(remoteEvent.getData(), AccountDeletionRequestedDTO.class);
                    break;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public JsonModel getLatestEvent(){
        return this.lastestEvent;
    }
}
