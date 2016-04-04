package se.omegapoint.accademy.opmarketplace.eventanalyzer.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.bus.Event;
import reactor.bus.EventBus;
import se.omegapoint.accademy.opmarketplace.eventanalyzer.domain.IncomingRemoteEvent;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/event")
public class EventReceiver {

    @Autowired
    EventBus eventBus;

    @RequestMapping(method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> receiveEvent(@RequestBody IncomingRemoteEvent event) {
        eventBus.notify("events", Event.wrap(event));
        return ResponseEntity.accepted().body(null);
    }
}
