package se.omegapoint.accademy.opmarketplace.eventanalyzer.domain;

import org.springframework.beans.factory.annotation.Value;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.commands.*;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.data_adapters.ItemAdapter;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.data_adapters.UserAdapter;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.infrastructure.json_representations.ItemDTO;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
    @Value("${mitigation.importantitem.duration}")
    private int IMPORTANT_ITEM_DURATION;

    private EventBus eventBus;
    private HashMap<String, SlidingWindow> eventWindows;
    private UserAdapter userAdapter;
    private ItemAdapter itemAdapter;
    private LocalTime timeAtFirstUserValidation;

    private Timer timer = new Timer();

    public Analyzer(EventBus eventBus, UserAdapter userAdapter, ItemAdapter itemAdapter) {
        this.eventBus = eventBus;
        this.userAdapter = userAdapter;
        this.itemAdapter = itemAdapter;
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
            case "ItemSearchRequested":
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
                boolean onlyImportantUsers = false;
                if (timeAtFirstUserValidation != null && timeAtFirstUserValidation.plusSeconds(1).isBefore(LocalTime.now())) {
                    List<String> importantUsers = userAdapter.fetchList(LocalDateTime.now().minusSeconds(IMPORTANT_USER_MEMBER_FOR), IMPORTANT_USER_MIN_SPEND);
                    System.out.println("DEBUG (importantUsers) " + Arrays.toString(importantUsers.toArray()));
                    onlyImportantUsers = true;
                    //create stronger validation
                    dispatchCommands(
                            new ValidateUsersDTO(DISABLE_DURATION, importantUsers, onlyImportantUsers),
                            new RateLimitFeatureDTO(RATE_LIMIT_INTERVAL, DISABLE_DURATION));

                } else if (timeAtFirstUserValidation == null){
                    List<String> importantUsers = userAdapter.fetchList(LocalDateTime.now().minusSeconds(IMPORTANT_USER_MEMBER_FOR), IMPORTANT_USER_MIN_SPEND);
                    System.out.println("DEBUG " + Arrays.toString(importantUsers.toArray()));
                    timeAtFirstUserValidation = LocalTime.now();
                    timer.schedule(new TimerReset(), TimeUnit.SECONDS.toMillis(DISABLE_DURATION/2));
                    dispatchCommands(
                            new ValidateUsersDTO(DISABLE_DURATION, importantUsers, onlyImportantUsers),
                            new RateLimitFeatureDTO(RATE_LIMIT_INTERVAL, DISABLE_DURATION));

                }
                break;
            case "ItemSearchRequested":
                System.out.println("DEBUG: Initiating default search response");
                ItemDTO mostImportantItem = itemAdapter.fetchMostImportantItemSince(LocalDateTime.now().minusDays(IMPORTANT_ITEM_DURATION));
                dispatchCommands(
                        new DefaultSearchResultDTO(DISABLE_DURATION, mostImportantItem));
                break;
            default:
                throw new IllegalStateException("Unknown event received.");
        }
    }

    private void dispatchCommands(Command... commands) {
        for (Command command : commands) {
            eventBus.notify("command", Event.wrap(command));
        }
    }

    private class TimerReset extends TimerTask {

        @Override
        public void run() {
            Analyzer.this.timeAtFirstUserValidation = null;
        }
    }
}
