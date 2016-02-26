package se.omegapoint.accademy.opmarketplace.messageservice.application.receivers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.bus.Event;
import reactor.bus.EventBus;
import se.omegapoint.accademy.opmarketplace.messageservice.domain.models.CommandEvent;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class CommandReceiver {

    @Autowired
    EventBus eventBus;

    @RequestMapping(value = "/command", method = POST)
    public ResponseEntity<Void> receiveCommand(
            @RequestParam("token") String token,
            @RequestBody CommandEvent commandEvent) {

        if (!token.equals("kebabpizza"))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        System.out.println("Rule command received ---> ");
        eventBus.notify("command", Event.wrap(commandEvent));

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }
}
