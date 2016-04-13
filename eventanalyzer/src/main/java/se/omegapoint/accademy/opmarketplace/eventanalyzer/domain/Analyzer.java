package se.omegapoint.accademy.opmarketplace.eventanalyzer.domain;

import org.springframework.beans.factory.annotation.Value;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.commands.Command;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.commands.DisableFeatureDTO;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.commands.RateLimitFeatureDTO;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.commands.ValidateUsersDTO;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.control_mechanisms.UserValidator;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class Analyzer implements Consumer<Event<RemoteEvent>> {

    @Value("${mitigation.threshold.window.size}")
    private int THRESHOLD_WINDOW_SIZE;
    @Value("${mitigation.threshold.window.time}")
    private int THRESHOLD_WINDOW_TIME;
    @Value("${mitigation.disable.duration}")
    private int DISABLE_DURATION;
    @Value("${mitigation.ratelimit.interval}")
    private int RATE_LIMIT_INTERVAL;
    @Value("${mitigation.importantuser.memberfor}")
    private int IMPORTANT_USER_MEMBER_FOR;
    @Value("${mitigation.importantuser.minspend}")
    private int IMPORTANT_USER_MIN_SPEND;

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
            eventWindows.put(eventType, new SlidingWindow(THRESHOLD_WINDOW_SIZE, THRESHOLD_WINDOW_TIME));
        }

        return eventWindows.get(eventType).isFull();
    }

    private void takeMitigatingAction(String eventType) {
        switch (eventType) {
            case "AccountCreationRequested":
                System.out.printf("DEBUG: Disabling %s events for %d seconds.%n", eventType, DISABLE_DURATION);
                dispatchCommands(
                        new DisableFeatureDTO(DISABLE_DURATION, eventType));
                break;
            case "ItemRequested":
                System.out.printf("DEBUG: Too many %s events.%n", eventType);
                List<String> importantUsers = userValidator.fetchList(LocalDateTime.now().minusSeconds(IMPORTANT_USER_MEMBER_FOR), IMPORTANT_USER_MIN_SPEND);
                dispatchCommands(
                        new ValidateUsersDTO(DISABLE_DURATION, importantUsers),
                        new RateLimitFeatureDTO(RATE_LIMIT_INTERVAL, DISABLE_DURATION));
                break;
        }
    }

    private void dispatchCommands(Command... commands) {
        for (Command command : commands) {
            eventBus.notify("command", Event.wrap(command));
        }
    }
}
