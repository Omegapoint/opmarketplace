package se.omegapoint.accademy.opmarketplace.messageservice.application.receivers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.bus.Event;
import reactor.bus.EventBus;
import se.omegapoint.accademy.opmarketplace.messageservice.domain.models.DomainEventModel;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/event")
public class EventReceiver {

    @Autowired
    EventBus eventBus;

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DomainEventModel> getExampleEvent() {
        DomainEventModel data = new DomainEventModel(
                "1",
                "12345",
                "Exempelaggregat",
                "Testevent",
                "Data 123",
                new Timestamp(1337));

        return ResponseEntity.ok(data);
    }

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<DomainEventModel> receiveEvent(
            @RequestParam("channel") String channel,
            @RequestBody DomainEventModel data) {

        System.out.print("Event received ---> ");
        eventBus.notify(channel, Event.wrap(data));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }
}