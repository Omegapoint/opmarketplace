package se.omegapoint.academy.opmarketplace.apigateway.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import se.omegapoint.academy.opmarketplace.apigateway.infrastructure.json_representations.objects.account.AccountDTO;

import java.util.ArrayList;
import java.util.Collection;

import static se.sawano.java.commons.lang.validate.Validate.notNull;

public class AuthenticationAccount implements UserDetails {

    private final AccountDTO accountDTO;

    public AuthenticationAccount(AccountDTO accountDTO) {
        this.accountDTO = notNull(accountDTO);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return accountDTO.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
