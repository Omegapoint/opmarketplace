package se.omegapoint.accademy.opmarketplace.eventanalyzer.domain;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.commands.DisableFeatureDTO;

import java.util.HashMap;

public class Analyzer implements Consumer<Event<RemoteEvent>> {

    // TODO: 04/04/16 Change to correct values
    private final int LIMIT_SIZE = 500;
    private final long LIMIT_TIME_MS = 1000;
    private final int DISABLE_DURATION_S = 20;

    private EventBus eventBus;
    private HashMap<String, SlidingWindow> eventWindows;

    public Analyzer(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.on(Selectors.object("events"), this);
        eventWindows = new HashMap<>();
    }

    @Override
    public void accept(Event<RemoteEvent> event) {
        // TODO: 04/04/16 filter, only some events
        RemoteEvent remoteEvent = event.getData();
        String eventType = remoteEvent.type;
        System.out.printf("Analyzing event with type %s%n", eventType);

        if (!eventWindows.containsKey(eventType)) {
            eventWindows.put(eventType, new SlidingWindow(LIMIT_SIZE, LIMIT_TIME_MS));
        }

        boolean overwhelmed = !eventWindows.get(eventType).put();

        if (overwhelmed) {
            // TODO: 04/04/16 Change event type;
            System.out.printf("TOO MANY EVENTS OF TYPE: %s%n", eventType);
            eventBus.notify("command", Event.wrap(new DisableFeatureDTO(DISABLE_DURATION_S, "AccountCreationRequested")));
        }
    }
}
