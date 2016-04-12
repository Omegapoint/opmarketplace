package se.omegapoint.accademy.opmarketplace.eventanalyzer.domain;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.commands.Command;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.commands.DisableFeatureDTO;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.commands.ValidateUsersDTO;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.control_mechanisms.UserValidator;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class Analyzer implements Consumer<Event<RemoteEvent>> {

    // TODO: 04/04/16 Change to correct values
    private final int LIMIT_SIZE = 20;
    private final long LIMIT_TIME_MS = 5000;
    private final int DISABLE_DURATION_S = 10;
    private final int IMPORTANT_USER_LIMIT_SECONDS = 5;
    private final int IMPORTANT_USER_MIN_SPEND = 10;

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

        if (isOverwhelmed(eventType)) {
            takeMitigatingAction(eventType);
        }
    }

    private boolean isOverwhelmed(String eventType) {
        if (!eventWindows.containsKey(eventType)) {
            eventWindows.put(eventType, new SlidingWindow(LIMIT_SIZE, LIMIT_TIME_MS));
        }

        return eventWindows.get(eventType).isFull();
    }

    private void takeMitigatingAction(String eventType) {
        Command command = null;
        switch (eventType) {
            case "AccountCreationRequested":
                System.out.printf("DEBUG: Disabling %s events for %d seconds.%n", eventType, DISABLE_DURATION_S);
                command = new DisableFeatureDTO(DISABLE_DURATION_S, eventType);
                break;
            case "ItemRequested":
                System.out.printf("DEBUG: Too many %s events.%n", eventType);
                List<String> importantUsers = userValidator.fetchList(LocalDateTime.now().minusSeconds(IMPORTANT_USER_LIMIT_SECONDS), IMPORTANT_USER_MIN_SPEND);
                command = new ValidateUsersDTO(DISABLE_DURATION_S, importantUsers);
                break;
        }

        eventBus.notify("command", Event.wrap(command));
    }
}
