package com.antazri.servermanager.service.impl;

import com.antazri.servermanager.dao.AdminDao;
import com.antazri.servermanager.models.Admin;
import com.antazri.servermanager.security.auth.AuthManager;
import com.antazri.servermanager.security.salt.dao.PsswdSaltDao;
import com.antazri.servermanager.security.salt.model.PsswdSalt;
import com.antazri.servermanager.service.AuthenticationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger logger = LogManager.getLogger(AuthenticationServiceImpl.class);

    private final AdminDao adminDao;
    private final PsswdSaltDao psswdSaltDao;
    private final AuthManager authManager;

    public AuthenticationServiceImpl(AdminDao adminDao, PsswdSaltDao psswdSaltDao, AuthManager authManager) {
        this.adminDao = adminDao;
        this.psswdSaltDao = psswdSaltDao;
        this.authManager = authManager;
    }

    @Override
    public Optional<Admin> login(String username, String candidatePassword) {
        if (!validateStringRequestBody(username) || !validateStringRequestBody(candidatePassword)) {
            logger.error("Error in credentials sent: login {} or password {}", username, candidatePassword);
            throw new IllegalArgumentException("Credentials not valid");
        }

        Optional<Admin> user = adminDao.findByUsername(username);

        if (user.isEmpty()) {
            logger.error("User {} does not exist in database", username);
            throw new NoSuchElementException("Username does not exist");
        }

        Optional<PsswdSalt> salt = psswdSaltDao.findByUserId(user.get().getId());

        if (salt.isEmpty()) {
            logger.error("Salt for User {} does not exist in database", username);
            throw new InternalAuthenticationServiceException("Server Error while trying to authenticate user");
        }

        boolean valid = authManager.validateCredentials(user.get(), candidatePassword, salt.get().getSalt());

        return valid ? user : Optional.empty();
    }

    private boolean validateStringRequestBody(String username) {
        return username != null && !username.isBlank();
    }
}
