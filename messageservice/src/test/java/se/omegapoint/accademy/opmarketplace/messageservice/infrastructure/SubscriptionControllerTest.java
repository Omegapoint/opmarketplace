package se.omegapoint.accademy.opmarketplace.messageservice.infrastructure;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.registry.Registration;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;
import se.omegapoint.accademy.opmarketplace.messageservice.Application;
import se.omegapoint.accademy.opmarketplace.messageservice.domain.RuleEngine;

import java.net.URI;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class SubscriptionControllerTest {

    @Autowired
    EventBus eventBus;

    @Autowired
    RuleEngine ruleEngine;

    @Test
    public void testSubscriptionIsSuccessful() throws Exception {
        SubscriptionController subscriptionController = new SubscriptionController(eventBus, ruleEngine);
        Assert.assertTrue(subscriptionController.subscribeEndpoint(new URI("http://test.xyz"), Selectors.object("channel_one")));
    }

    @Test
    public void testDispatchersAreSubscribedToCorrectChannel() throws Exception {
        SubscriptionController subscriptionController = new SubscriptionController(eventBus, ruleEngine);
        subscriptionController.subscribeEndpoint(new URI("http://test.xyz"), Selectors.object("channel_one"));
        Assert.assertEquals(1, eventBus.getConsumerRegistry().select("channel_one").size());
        Assert.assertEquals(0, eventBus.getConsumerRegistry().select("channel_two").size());
    }

    @Test
    public void testOneEndpointCannotSubscribeTwiceToSameChannel() throws Exception {
        SubscriptionController subscriptionController = new SubscriptionController(eventBus, ruleEngine);
        Assert.assertTrue(subscriptionController.subscribeEndpoint(new URI("http://test.xyz"), Selectors.object("channel_one")));
        Assert.assertFalse(subscriptionController.subscribeEndpoint(new URI("http://test.xyz"), Selectors.object("channel_one")));
    }
}