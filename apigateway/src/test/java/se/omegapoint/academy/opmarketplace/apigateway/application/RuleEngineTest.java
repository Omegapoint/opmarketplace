package se.omegapoint.academy.opmarketplace.apigateway.application;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RuleEngineTest {

    RuleEngine ruleEngine = new RuleEngine();

    @Test
    public void should_deny_event() throws Exception {
        String eventName = "ExampleEvent";
        ruleEngine.deny(eventName, 1);
        assertFalse(ruleEngine.shouldAllowEvent(eventName));
    }

    @Test
    public void should_allow_event() throws Exception {
        String eventName = "ExampleEvent";
        assertTrue(ruleEngine.shouldAllowEvent(eventName));
    }

    @Test
    public void should_allow_event_after_deny_expiration() throws Exception {
        String eventName = "ExampleEvent";
        ruleEngine.deny(eventName, 1);
        Thread.sleep(1500);
        assertTrue(ruleEngine.shouldAllowEvent(eventName));
    }

    @Test
    public void should_allow_user() throws Exception {
        String email = "test@email.com";
        assertTrue(ruleEngine.shouldAllowUser(email));
    }

    @Test
    public void should_allow_user_who_is_on_the_list() throws Exception {
        String email = "test@email.com";
        ruleEngine.allowUsers(Arrays.asList(email), 1);
        assertTrue(ruleEngine.shouldAllowUser(email));
    }

    @Test
    public void should_deny_user_who_is_not_on_the_list() throws Exception {
        String email = "test@email.com";
        ruleEngine.allowUsers(Arrays.asList(email), 1);
        assertFalse(ruleEngine.shouldAllowUser("not_test@email.com"));
    }

}