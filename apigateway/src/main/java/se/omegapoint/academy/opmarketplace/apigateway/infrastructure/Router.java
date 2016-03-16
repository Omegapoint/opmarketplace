package se.omegapoint.academy.opmarketplace.apigateway.infrastructure;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.*;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class Router {

    private EventBus eventBus;

    public Router(EventBus eventBus) {
        this.eventBus = notNull(eventBus);
    }

    public void publish(AccountCreatedModel model){
        eventBus.notify(CHANNEL.ACCOUNTCREATION.NAME + model.getEmail().getAddress(), Event.wrap(model));
    }

    public void publish(AccountNotCreatedModel model){
        eventBus.notify(CHANNEL.ACCOUNTCREATION.NAME + model.getEmail().getAddress(), Event.wrap(model));
    }

    public void publish(AccountObtainedModel model){
        eventBus.notify(CHANNEL.ACCOUNTREQUEST.NAME + model.getAccount().getEmail().getAddress(), Event.wrap(model));
    }

    public void publish(AccountNotObtainedModel model){
        eventBus.notify(CHANNEL.ACCOUNTREQUEST.NAME + model.getEmail().getAddress(), Event.wrap(model));
    }

    public void publish(AccountUserChangedModel model) {
        eventBus.notify(CHANNEL.ACCOUNTUSERCHANGE.NAME + model.getAggregateIdentity().getId(), Event.wrap(model));
    }

    public void publish(AccountUserNotChangedModel model) {
        eventBus.notify(CHANNEL.ACCOUNTUSERCHANGE.NAME + model.getAggregateIdentity().getId(), Event.wrap(model));
    }

    public void subscribe(CHANNEL channel, String id, Consumer consumer){
        eventBus.on(Selectors.regex(channel.NAME+ "(\\s*)" + id + "(\\s*)"), consumer).cancelAfterUse();
    }

    public enum CHANNEL {
        ACCOUNTCREATION("AccountCreation"), ACCOUNTREQUEST("AccountRequest"), ACCOUNTUSERCHANGE("AccountUserChange");

        public final String NAME;

        CHANNEL(String name) {
            this.NAME = name;
        }
    }
}
