package se.omegapoint.accademy.opmarketplace.messageservice.application.receivers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.bus.Event;
import reactor.bus.EventBus;
import se.omegapoint.accademy.opmarketplace.messageservice.domain.models.RemoteEvent;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/event")
public class EventReceiver {

    @Autowired
    EventBus eventBus;

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<RemoteEvent> receiveEvent(
            @RequestParam("channel") String channel,
            @RequestBody String data) {

        System.out.printf("Event received and published on channel %s...%n", channel);
        eventBus.notify(channel, Event.wrap(data));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }
}