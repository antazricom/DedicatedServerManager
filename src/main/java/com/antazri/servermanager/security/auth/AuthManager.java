package com.antazri.servermanager.security.auth;

import com.antazri.servermanager.models.Admin;

@FunctionalInterface
public interface AuthManager {

    boolean validateCredentials(Admin admin, String candidatePassword, String salt);
}
