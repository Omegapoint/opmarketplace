package se.omegapoint.academy.opmarketplace.apigateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.TestPublisher;

@Configuration
@Profile("test")
public class TestConfiguration {

    @Bean
    public TestPublisher createRemoteEventPublisher(){
        return new TestPublisher();
    }
}
