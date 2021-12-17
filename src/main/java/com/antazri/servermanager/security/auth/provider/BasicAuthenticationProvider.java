package com.antazri.servermanager.security.auth.provider;

import com.antazri.servermanager.models.Admin;
import com.antazri.servermanager.service.AuthenticationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class BasicAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LogManager.getLogger(BasicAuthenticationProvider.class);

    private final AuthenticationService authenticationService;

    public BasicAuthenticationProvider(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<Admin> login = authenticationService.login(name, password);

        if (login.isPresent()) {
            return new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>());
        } else {
            logger.error("Unauthorized: authentication request refused");
            throw new AuthenticationServiceException("Unauthorized: authentication request refused");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
