package se.omegapoint.academy.opmarketplace.apigateway;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AccountAuthenticationService implements AuthenticationProvider{

    private final AccountRetrieverService accounts;

    public AccountAuthenticationService(AccountRetrieverService accounts) {
        this.accounts = notNull(accounts);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        try {
            AccountAuth account = (AccountAuth) accounts.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(account, "", account.getAuthorities());
        } catch (UsernameNotFoundException e){
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
