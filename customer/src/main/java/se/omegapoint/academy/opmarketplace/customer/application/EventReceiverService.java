package se.omegapoint.academy.opmarketplace.customer.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.bus.Event;
import reactor.bus.EventBus;
import se.omegapoint.academy.opmarketplace.customer.application.json_representations.DomainEventModel;
import se.sawano.java.commons.lang.validate.IllegalArgumentValidationException;

@RestController
@RequestMapping("/event")
public class EventReceiverService {

    @Autowired
    EventBus eventBus;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity eventInput(@RequestBody DomainEventModel event) {
//        try {
//            DomainEventOLD domainEventOLD = new DomainEventOLD(event);
//            System.out.println(domainEventOLD.eventData());
//            eventBus.notify("circle", Event.wrap(domainEventOLD));
//            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
//        }catch (IllegalArgumentException | IllegalArgumentValidationException e) {
//            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
//        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
