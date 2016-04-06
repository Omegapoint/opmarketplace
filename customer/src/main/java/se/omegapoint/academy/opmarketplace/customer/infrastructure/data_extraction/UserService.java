package se.omegapoint.academy.opmarketplace.customer.infrastructure.data_extraction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_models.AccountCreatedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_persistance.AccountCreatedJPA;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class UserService {

    @Autowired
    AccountCreatedJPA accountCreatedJPA;

    @RequestMapping(value = "/users", method = GET)
    public List<String> getUsers (
            @RequestParam("member_since") String memberSince,
            @RequestParam("min_spend") int minSpend) {

        // TODO: 06/04/16 Implement min spend.
        LocalDateTime dateTime = LocalDateTime.parse(memberSince);
        Timestamp timestamp = new Timestamp(dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        List<String> users = accountCreatedJPA.findByTimeLessThan(timestamp).stream()
                .map(AccountCreatedModel::domainEvent)
                .map(event -> event.email().address())
                .collect(Collectors.toList());

        return users;
    }
}
