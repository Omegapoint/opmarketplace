package se.omegapoint.academy.opmarketplace.apigateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.Environment;
import reactor.bus.EventBus;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.EventReceiverService;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.Router;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.account.*;

import java.util.HashMap;

@Configuration
public class MainConfiguration {

    @Bean
    Environment env() {
        return Environment.initializeIfEmpty()
                .assignErrorJournal();
    }

    @Bean
    EventBus createEventBus(Environment env) {
        return EventBus.create(env, Environment.THREAD_POOL);
    }

    @Bean
    Router createRouter(EventBus eventBus){
        return new Router(eventBus);
    }

    @Bean
    EventReceiverService createEventReceiverService(Router router){
        HashMap<String, Class> eventTypes = new HashMap<>();
        eventTypes.put(AccountCreatedDTO.TYPE, AccountCreatedDTO.class);
        eventTypes.put(AccountNotCreatedDTO.TYPE, AccountNotCreatedDTO.class);
        eventTypes.put(AccountDeletedDTO.TYPE, AccountDeletedDTO.class);
        eventTypes.put(AccountNotDeletedDTO.TYPE, AccountNotDeletedDTO.class);
        eventTypes.put(AccountObtainedDTO.TYPE, AccountObtainedDTO.class);
        eventTypes.put(AccountNotObtainedDTO.TYPE, AccountNotObtainedDTO.class);
        eventTypes.put(AccountUserChangedDTO.TYPE, AccountUserChangedDTO.class);
        eventTypes.put(AccountUserNotChangedDTO.TYPE, AccountUserNotChangedDTO.class);

        return new EventReceiverService(router, eventTypes);
    }

}
