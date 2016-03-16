package se.omegapoint.academy.opmarketplace.apigateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.RemoteEventPublisher;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.AccountCreationRequestedModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.AccountRequestedModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.AccountUserChangeRequestedModel;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.RemoteEvent;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.JsonModel;

import java.io.IOException;

@Configuration
@Profile("test")
public class TestConfiguration {

    @Bean
    public TestPublisher createRemoteEventPublisher(){
        return new TestPublisher();
    }

    public class TestPublisher implements RemoteEventPublisher {
        private ObjectMapper json = new ObjectMapper();
        private JsonModel lastestEvent;
        @Override
        public void publish(RemoteEvent remoteEvent) {
            try {
                switch (remoteEvent.getType()) {
                    case AccountCreationRequestedModel.TYPE:
                        lastestEvent = json.readValue(remoteEvent.getData(), AccountCreationRequestedModel.class);
                        break;
                    case AccountRequestedModel.TYPE:
                        lastestEvent = json.readValue(remoteEvent.getData(), AccountRequestedModel.class);
                        break;
                    case AccountUserChangeRequestedModel.TYPE:
                        lastestEvent = json.readValue(remoteEvent.getData(), AccountUserChangeRequestedModel.class);
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
}
