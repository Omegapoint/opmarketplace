package se.omegapoint.academy.opmarketplace.apigateway.infrastructure;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.account.*;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.events.incoming.item.*;

import java.util.HashMap;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class Router {

    private EventBus eventBus;

    public Router(EventBus eventBus) {
        this.eventBus = notNull(eventBus);
    }

    public void publish(AccountCreatedDTO model){
        eventBus.notify(model.requestId(), Event.wrap(model));
    }

    public void publish(AccountNotCreatedDTO model){
        eventBus.notify(model.requestId(), Event.wrap(model));
    }

    public void publish(AccountObtainedDTO model){
        eventBus.notify(model.requestId(), Event.wrap(model));
    }

    public void publish(AccountNotObtainedDTO model){
        eventBus.notify(model.requestId(), Event.wrap(model));
    }

    public void publish(AccountUserChangedDTO model) {
        eventBus.notify(model.requestId(), Event.wrap(model));
    }

    public void publish(AccountUserNotChangedDTO model) {
        eventBus.notify(model.requestId(), Event.wrap(model));
    }

    public void publish(AccountDeletedDTO model) {
        eventBus.notify(model.requestId(), Event.wrap(model));
    }

    public void publish(AccountNotDeletedDTO model) {
        eventBus.notify(model.requestId(), Event.wrap(model));
    }

    public void publish(ItemCreatedDTO model) {
        eventBus.notify(model.requestId(), Event.wrap(model));
    }

    public void publish(ItemNotCreatedDTO model) {
        eventBus.notify(model.requestId(), Event.wrap(model));
    }

    public void publish(ItemObtainedDTO model) {
        eventBus.notify(model.requestId(), Event.wrap(model));
    }

    public void publish(ItemNotObtainedDTO model) {
        eventBus.notify(model.requestId(), Event.wrap(model));
    }

    public void publish(ItemSearchCompletedDTO model) {
        eventBus.notify(model.requestId(), Event.wrap(model));
    }

    public void publish(ItemsNotSearchedDTO model) {
        eventBus.notify(model.requestId(), Event.wrap(model));
    }

    public void subscribe(String id, Consumer consumer){
        eventBus.on(Selectors.object(id), consumer).cancelAfterUse();
    }
}
