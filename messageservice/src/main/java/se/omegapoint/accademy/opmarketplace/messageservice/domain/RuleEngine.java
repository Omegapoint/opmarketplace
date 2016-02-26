package se.omegapoint.accademy.opmarketplace.messageservice.domain;


import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;
import se.omegapoint.accademy.opmarketplace.messageservice.domain.models.DomainEventModel;
import se.omegapoint.accademy.opmarketplace.messageservice.domain.models.CommandEvent;

import java.util.HashSet;

public class RuleEngine implements Consumer<Event<CommandEvent>> {

    HashSet<String> prohibitedEvents;

    public RuleEngine(EventBus eventBus) {
        eventBus.on(Selectors.object("command"), this);
        prohibitedEvents = new HashSet<>();
    }

    public boolean allow(DomainEventModel event) {
        return !prohibitedEvents.contains(event.getEventType());
    }

    @Override
    public void accept(Event<CommandEvent> commandEvent) {
        CommandEvent command = commandEvent.getData();
        if (command.isAllow()) {
            prohibitedEvents.remove(command.getEventType());
        } else if (!command.isAllow()) {
            prohibitedEvents.add(command.getEventType());
        }

        System.out.printf("Event %s is now allowed: %b%n", command.getEventType(), command.isAllow());
    }
}
