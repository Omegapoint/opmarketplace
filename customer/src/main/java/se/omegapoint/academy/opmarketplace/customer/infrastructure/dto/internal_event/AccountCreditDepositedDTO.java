package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.internal_event;

import se.omegapoint.academy.opmarketplace.customer.domain.events.persistable.AccountCreditDeposited;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Event;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.CreditDTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object.EmailDTO;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountCreditDepositedDTO implements Event, Serializer {

    public static final String TYPE = "AccountCreditDeposited";

    public final String requestId;
    public final EmailDTO email;
    public final CreditDTO credit;

    public AccountCreditDepositedDTO(AccountCreditDeposited accountCreditDeposited, String requestId) {
        notNull(accountCreditDeposited);
        this.requestId = notNull(requestId);
        this.email = new EmailDTO(accountCreditDeposited.email());
        this.credit = new CreditDTO(accountCreditDeposited.credit());
    }

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public String requestId() {
        return requestId;
    }
}
