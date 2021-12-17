package com.antazri.servermanager.service;

import com.antazri.servermanager.models.Admin;

import java.util.Optional;

public interface AuthenticationService {

    Optional<Admin> login(String username, String password);
}
