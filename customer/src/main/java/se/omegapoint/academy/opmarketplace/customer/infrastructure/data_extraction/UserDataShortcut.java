package se.omegapoint.academy.opmarketplace.customer.infrastructure.data_extraction;

import org.springframework.beans.factory.annotation.Autowired;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_models.AccountCreatedModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_models.AccountCreditWithdrawnModel;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_persistance.AccountCreatedJPA;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.persistence.event_persistance.AccountCreditWithdrawnJPA;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class UserDataShortcut {

    @Autowired
    AccountCreatedJPA accountCreatedJPA;

    @Autowired
    AccountCreditWithdrawnJPA accountCreditWithdrawnJPA;

    protected List<String> getMembersSince(Timestamp timestamp) {
        return accountCreatedJPA.findByTimeLessThan(timestamp).stream()
                .map(AccountCreatedModel::domainEvent)
                .map(event -> event.email().address())
                .collect(Collectors.toList());
    }

    protected List<String> filterOnPurchases(List<String> users, int minSpend) {
        return users.stream().filter(email ->
                accountCreditWithdrawnJPA.findByEmailOrderByTime(email).stream()
                        .map(AccountCreditWithdrawnModel::domainEvent)
                        .mapToInt(event -> event.credit().amount())
                        .sum() >= minSpend)
                .collect(Collectors.toList());
    }
}
