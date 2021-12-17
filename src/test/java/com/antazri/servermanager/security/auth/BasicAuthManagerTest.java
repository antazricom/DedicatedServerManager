package com.antazri.servermanager.security.auth;

import com.antazri.servermanager.models.Admin;
import com.antazri.servermanager.models.AdminRole;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;

class BasicAuthManagerTest {

    private final BasicAuthManager basicAuthManager = new BasicAuthManager();

    @Test
    void whenGettingInvalidRequestElements_shouldThrowException() {
        // When
        Admin admin = new Admin(1, "root", "$2a$10$.3mO/9nsVklhnQAPJPvRcuPAzHeGh6uNcSrEE0d1YZrBzWr7IE6XS", AdminRole.DEVELOPER);
        String candidatePwd = "";
        String salt = "    ";

        // Then
        assertThrows(IllegalArgumentException.class, () -> basicAuthManager.validateCredentials(admin, candidatePwd, salt));
    }

    @Test
    void whenCredentialsAreValid_shouldReturnTrue() {
        // Given
        Admin admin = new Admin(1, "root", "$2a$10$.3mO/9nsVklhnQAPJPvRcuPAzHeGh6uNcSrEE0d1YZrBzWr7IE6XS", AdminRole.DEVELOPER);
        String candidatePwd = "root";
        String salt = "root";

        // When
        boolean valid = basicAuthManager.validateCredentials(admin, candidatePwd, salt);

        // Then
        assertTrue(valid);
    }

    @Test
    void whenCredentialsAreNotValid_shouldReturnFalse() {
        // Given
        Admin admin = new Admin(1, "root", "$2a$10$.3mO/9nsVklhnQAPJPvRcuPAzHeGh6uNcSrEE0d1YZrBzWr7IE6XS", AdminRole.DEVELOPER);
        String candidatePwd = "route";
        String salt = "root";

        // When
        boolean valid = basicAuthManager.validateCredentials(admin, candidatePwd, salt);

        // Then
        assertFalse(valid);
    }


}