package se.omegapoint.accademy.opmarketplace.messageservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.bus.Event;
import reactor.bus.EventBus;
import se.omegapoint.accademy.opmarketplace.messageservice.models.DomainEventModel;

import java.sql.Timestamp;

@RestController
@RequestMapping("/event")
public class EventReceiver {

    @Autowired
    EventBus eventBus;

    @RequestMapping
    public ResponseEntity<DomainEventModel> get() {
        DomainEventModel data = new DomainEventModel(
                "12345",
                "Exempelaggregat",
                "Testevent",
                "Data 123",
                new Timestamp(System.currentTimeMillis()));

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DomainEventModel> eventInput(@RequestBody DomainEventModel data) {
        eventBus.notify("event_posted", Event.wrap(data));
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}