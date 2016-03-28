package se.omegapoint.academy.opmarketplace.marketplace;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import se.omegapoint.academy.opmarketplace.marketplace.infrastructure.TestPublisher;

@Configuration
@Profile("test")
public class TestConfiguration {

    @Bean
    public TestPublisher createRemoteEventPublisher(){
        return new TestPublisher();
    }
}
