package se.omegapoint.academy.opmarketplace.apigateway.application;

import org.junit.Test;

import static org.junit.Assert.*;

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
}