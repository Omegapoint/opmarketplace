package se.omegapoint.academy.opmarketplace.apigateway;

import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    .csrf().disable()
                .authorizeRequests()
                .antMatchers("/accounts/credit/deposit").authenticated()
                .antMatchers("/accounts/credit/withdraw").authenticated()
                .anyRequest().permitAll()
                .and().httpBasic();
    }

    @Bean(destroyMethod = "cleanup")
    AccountRetrieverService accountAuthService(){
        return new AccountRetrieverService(HttpClients.createDefault());
    }

    @Bean
    AccountAuthenticationService authenticationService(AccountRetrieverService retrieverService){
        return new AccountAuthenticationService(retrieverService);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, AuthenticationProvider authenticationProvider) throws Exception {
        auth
                .authenticationProvider(authenticationProvider);
    }
}

