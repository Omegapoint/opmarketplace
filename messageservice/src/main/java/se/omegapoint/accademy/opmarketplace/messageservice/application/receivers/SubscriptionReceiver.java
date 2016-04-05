package se.omegapoint.accademy.opmarketplace.messageservice.application.receivers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.bus.selector.Selectors;
import se.omegapoint.accademy.opmarketplace.messageservice.infrastructure.SubscriptionController;

import java.net.URL;
import java.util.HashMap;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class SubscriptionReceiver {

    @Autowired
    private SubscriptionController subscriptionController;


    @RequestMapping(value = "/subscribe", method = POST)
    public ResponseEntity<Void> subscribe(
            @RequestParam("channel") String channel,
            @RequestParam("endpoint") URL endpoint) {

        subscriptionController.subscribeEndpoint(endpoint, Selectors.object(channel));

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

    @RequestMapping(value = "/subscribe_all", method = POST)
    public ResponseEntity<Void> subscribeAll(
            @RequestParam("endpoint") URL endpoint,
            @RequestParam("token") String token) {

        //TODO: maybe improve security
        if (!token.equals("kebabpizza")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } else {
            subscriptionController.subscribeEndpoint(endpoint, Selectors.regex("\\w+"));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        }
    }

    @RequestMapping(value = "/subscriptions", method = GET)
    public ResponseEntity<HashMap> getSubscriptions() {
        return ResponseEntity.ok().body(subscriptionController.subscriptions());
    }
}
