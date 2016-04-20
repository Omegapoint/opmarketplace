package se.omegapoint.academy.opmarketplace.customer.domain.events;

import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Credit;
import se.omegapoint.academy.opmarketplace.customer.domain.value_objects.Email;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountCreditWithdrawalRequested {
    private final Email email;
    private final Credit credits;

    public AccountCreditWithdrawalRequested(Email email, Credit credits) {
        this.email = notNull(email);
        this.credits = notNull(credits);
    }

    public Email email() {
        return email;
    }

    public Credit credits() {
        return credits;
    }
}
