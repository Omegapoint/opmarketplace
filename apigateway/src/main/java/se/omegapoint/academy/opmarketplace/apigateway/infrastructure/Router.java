package se.omegapoint.academy.opmarketplace.apigateway.infrastructure;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.*;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class Router {

    private EventBus eventBus;

    public Router(EventBus eventBus) {
        this.eventBus = notNull(eventBus);
    }

    public void publish(AccountCreatedDTO model){
        eventBus.notify(CHANNEL.ACCOUNTCREATION.NAME + model.email.address, Event.wrap(model));
    }

    public void publish(AccountNotCreatedDTO model){
        eventBus.notify(CHANNEL.ACCOUNTCREATION.NAME + model.email.address, Event.wrap(model));
    }

    public void publish(AccountObtainedDTO model){
        eventBus.notify(CHANNEL.ACCOUNTREQUEST.NAME + model.account.email.address, Event.wrap(model));
    }

    public void publish(AccountNotObtainedDTO model){
        eventBus.notify(CHANNEL.ACCOUNTREQUEST.NAME + model.email.address, Event.wrap(model));
    }

    public void publish(AccountUserChangedDTO model) {
        eventBus.notify(CHANNEL.ACCOUNTUSERCHANGE.NAME + model.email.address, Event.wrap(model));
    }

    public void publish(AccountUserNotChangedDTO model) {
        eventBus.notify(CHANNEL.ACCOUNTUSERCHANGE.NAME + model.email.address, Event.wrap(model));
    }

    public void publish(AccountDeletedDTO model) {
        eventBus.notify(CHANNEL.ACCOUNTDELETED.NAME + model.email.address, Event.wrap(model));
    }

    public void publish(AccountNotDeletedDTO model) {
        eventBus.notify(CHANNEL.ACCOUNTDELETED.NAME + model.email.address, Event.wrap(model));
    }

    public void subscribe(CHANNEL channel, String id, Consumer consumer){
        eventBus.on(Selectors.regex(channel.NAME + "(\\s*)" + id + "(\\s*)"), consumer).cancelAfterUse();
    }

    public enum CHANNEL {
        ACCOUNTCREATION("AccountCreation"), ACCOUNTREQUEST("AccountRequest"),
        ACCOUNTUSERCHANGE("AccountUserChanged"), ACCOUNTDELETED("AccountDeleted");

        public final String NAME;

        CHANNEL(String name) {
            this.NAME = name;
        }
    }
}
