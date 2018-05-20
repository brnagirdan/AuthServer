package com.oauth.config;

import com.oauth.repository.OAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Configuration
public class WebSecurityConfig extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    OAuthRepository oAuthRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    UserDetailsService userDetailsService() {
        return (email) -> oAuthRepository
                .findByEmail(email)
                .map(a -> new User(a.getEmail(), a.getPassword(), true, true, true, true,

                        AuthorityUtils.createAuthorityList("USER", "write")))



                .orElseThrow(

                        () -> new UsernameNotFoundException("could not find the user '"

                                + email + "'"));
    }

}