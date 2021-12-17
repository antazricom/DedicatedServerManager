package com.antazri.servermanager.service.impl;

import com.antazri.servermanager.dao.AdminDao;
import com.antazri.servermanager.models.Admin;
import com.antazri.servermanager.models.AdminRole;
import com.antazri.servermanager.security.auth.AuthManager;
import com.antazri.servermanager.security.auth.BasicAuthManager;
import com.antazri.servermanager.security.salt.dao.PsswdSaltDao;
import com.antazri.servermanager.security.salt.model.PsswdSalt;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthenticationServiceImplTest {

    private final AdminDao adminDao = mock(AdminDao.class);
    private final PsswdSaltDao psswdSaltDao = mock(PsswdSaltDao.class);
    private final AuthManager authManager = mock(BasicAuthManager.class);
    private final AuthenticationServiceImpl authenticationService = new AuthenticationServiceImpl(adminDao, psswdSaltDao, authManager);

    @Test
    void whenLoggingWithInvalidUsername_shouldThrowException() {
        // Given
        String username = "  ";
        String password = "root";

        // Then
        assertThrows(IllegalArgumentException.class, () -> authenticationService.login(username, password));
    }

    @Test
    void whenLoggingWithInvalidPassword_shouldThrowException() {
        // Given
        String username = "root";
        String password = "   ";

        // Then
        assertThrows(IllegalArgumentException.class, () -> authenticationService.login(username, password));
    }

    @Test
    void whenLoggingWithUnknownUsername_shouldThrowException() {
        // Given
        String username = "route";
        String password = "root";

        // When
        when(adminDao.findByUsername("route")).thenReturn(Optional.empty());

        // Then
        assertThrows(NoSuchElementException.class, () -> authenticationService.login(username, password));
    }

    @Test
    void whenLoggingWithUsernameWithoutSalt_shouldThrowException() {
        // Given
        String username = "root";
        String password = "root";

        // When
        when(adminDao.findByUsername("root"))
                .thenReturn(Optional.of(new Admin(1, "root", "$2a$10$.3mO/9ns" +
                        "VklhnQAPJPvRcuPAzHeGh6uNcSrEE0d1YZrBzWr7IE6XS", AdminRole.MAINTAINER)));
        when(psswdSaltDao.findByUserId(anyInt())).thenReturn(Optional.empty());

        // Then
        assertThrows(InternalAuthenticationServiceException.class, () -> authenticationService.login(username, password));
    }


    @Test
    void whenLoginCredentialsAreValid_shouldReturnAdminEntity() {
        // Given
        String username = "root";
        String password = "root";

        // When
        when(adminDao.findByUsername("root"))
                .thenReturn(Optional.of(new Admin(1, "root", "$2a$10$.3mO/9ns" +
                        "VklhnQAPJPvRcuPAzHeGh6uNcSrEE0d1YZrBzWr7IE6XS", AdminRole.MAINTAINER)));
        when(psswdSaltDao.findByUserId(anyInt())).thenReturn(Optional.of(new PsswdSalt("1", 1, "root")));
        when(authManager.validateCredentials(any(), any(), any())).thenReturn(true);

        Optional<Admin> login = authenticationService.login(username, password);

        // Then
        Admin expectedAdmin = new Admin(1, "root", "$2a$10$.3mO/9ns" +
                "VklhnQAPJPvRcuPAzHeGh6uNcSrEE0d1YZrBzWr7IE6XS", AdminRole.MAINTAINER);

        assertAll(() -> {
            assertTrue(login.isPresent());
            assertEquals(expectedAdmin, login.get());
        });
    }
}