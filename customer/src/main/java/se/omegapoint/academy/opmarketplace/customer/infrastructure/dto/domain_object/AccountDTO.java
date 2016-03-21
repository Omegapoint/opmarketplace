package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object;

import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountDTO implements DTO, Serializer, Deserializer<Account> {
    public final EmailDTO email;
    public final UserDTO user;

    public AccountDTO(Account account) {
        notNull(account);
        this.email = new EmailDTO(account.email());
        this.user = new UserDTO(account.user());
    }

    @Override
    public Account domainObject() {
        return new Account(email.domainObject(), user.domainObject());
    }
}
