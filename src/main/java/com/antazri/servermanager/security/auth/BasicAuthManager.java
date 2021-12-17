package com.antazri.servermanager.security.auth;

import com.antazri.servermanager.models.Admin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthManager implements AuthManager {

    private static final Logger logger = LogManager.getLogger(BasicAuthManager.class);

    @Override
    public boolean validateCredentials(Admin admin, String candidatePassword, String salt) {
        if (!validateRequestElements(admin, candidatePassword, salt)) {
            logger.error("Error(s) in provided credentials");
            throw new IllegalArgumentException("Error(s) in provided credentials");
        }

        String fullPassword = candidatePassword + salt;

        return BCrypt.checkpw(fullPassword, admin.getPassword());
    }

    private boolean validateRequestElements(Admin admin, String pswwd, String encodedSalt) {
        return admin != null
                && pswwd != null
                && encodedSalt != null
                && !pswwd.isBlank()
                && !encodedSalt.isBlank();
    }
}
