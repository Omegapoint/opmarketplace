package se.omegapoint.accademy.opmarketplace.eventanalyzer.domain;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.commands.DisableFeatureDTO;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.control_mechanisms.UserValidator;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Analyzer implements Consumer<Event<RemoteEvent>> {

    // TODO: 04/04/16 Change to correct values
    private final int LIMIT_SIZE = 1;
    private final long LIMIT_TIME_MS = 10000;
    private final int DISABLE_DURATION_S = 20;
    private final int IMPORTANT_USER_LIMIT_MINUTES = 2;

    private EventBus eventBus;
    private HashMap<String, SlidingWindow> eventWindows;
    private UserValidator userValidator;

    public Analyzer(EventBus eventBus, UserValidator userValidator) {
        this.eventBus = eventBus;
        this.userValidator = userValidator;
        eventBus.on(Selectors.object("events"), this);
        eventWindows = new HashMap<>();
    }

    @Override
    public void accept(Event<RemoteEvent> event) {
        RemoteEvent remoteEvent = event.getData();
        String eventType = remoteEvent.type;

        switch (eventType) { // Filter events
            case "AccountCreationRequested":
            case "ItemRequested":
                analyze(eventType);
                break;
        }
    }

    private void analyze(String eventType) {
        System.out.printf("Analyzing event with type %s%n", eventType);

        if (!eventWindows.containsKey(eventType)) {
            eventWindows.put(eventType, new SlidingWindow(LIMIT_SIZE, LIMIT_TIME_MS));
        }

        boolean overwhelmed = !eventWindows.get(eventType).put();

        if (overwhelmed) {
            takeMitigatingAction(eventType);
        }
    }

    private void takeMitigatingAction(String eventType) {
        switch (eventType) {
            case "AccountCreationRequested":
                System.out.printf("DEBUG: Disabling %s events for %d seconds.%n", eventType, DISABLE_DURATION_S);
                eventBus.notify("command", Event.wrap(new DisableFeatureDTO(DISABLE_DURATION_S, eventType)));
                break;
            case "ItemRequested":
                System.out.printf("DEBUG: Too many %s events.%n", eventType);
                fetchImportantUsers();
                break;
        }
    }

    private void fetchImportantUsers() {
        LocalDateTime timeLimit = LocalDateTime.now().minusMinutes(IMPORTANT_USER_LIMIT_MINUTES);
        userValidator.fetchList(timeLimit);
        userValidator.publishList();
    }
}
