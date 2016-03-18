package se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.domain_object;

import se.omegapoint.academy.opmarketplace.customer.domain.entities.Account;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.DTO;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Deserializer;
import se.omegapoint.academy.opmarketplace.customer.infrastructure.dto.Serializer;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountModel implements DTO, Serializer, Deserializer<Account> {
    private EmailModel email;
    private UserModel user;

    public AccountModel(){}

    public AccountModel(Account account) {
        notNull(account);
        this.email = new EmailModel(account.email());
        this.user = new UserModel(account.user());
    }

    public EmailModel getEmail() {
        return email;
    }

    public UserModel getUser() {
        return user;
    }

    @Override
    public Account domainObject() {
        return new Account(email.domainObject(), user.domainObject());
    }
}
