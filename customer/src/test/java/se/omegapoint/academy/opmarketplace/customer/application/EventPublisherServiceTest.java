package se.omegapoint.academy.opmarketplace.customer.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.bus.Event;
import reactor.bus.EventBus;
import se.omegapoint.academy.opmarketplace.customer.CustomerApplication;
import se.omegapoint.academy.opmarketplace.customer.domain.Account;
import se.omegapoint.academy.opmarketplace.customer.domain.events.DomainEvent;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.event_data_objects.AccountCreated;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CustomerApplication.class)
public class EventPublisherServiceTest {

    @Autowired
    EventBus eventBus;

    @Test
    public void should_be_received_by_event_publisher(){

        String email = "initial@initial.com";
        String firstName = "initial";
        String lastName = "initial";
        eventBus.notify("publish", Event.wrap(new DomainEvent(email, Account.class, new AccountCreated(email, firstName, lastName))));

        CountDownLatch latch = new CountDownLatch(1);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}