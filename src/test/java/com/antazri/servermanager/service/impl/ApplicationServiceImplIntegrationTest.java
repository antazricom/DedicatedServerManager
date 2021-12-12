package com.antazri.servermanager.service.impl;

import com.antazri.servermanager.DedicatedServerManagerApplication;
import com.antazri.servermanager.dao.impl.ApplicationDaoImpl;
import com.antazri.servermanager.models.Application;
import com.antazri.servermanager.service.ApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        classes = {DedicatedServerManagerApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ApplicationServiceImplIntegrationTest {

    @Autowired
    private ApplicationService applicationService;

    @Test
    void when_should() {
        // Given
        int appId = 1;

        // When
        Optional<Application> app = applicationService.getById(appId);

        // Then
        Application expectedApp = Application.from(1, "Application 1", Collections.emptyList());
        assertAll(() -> {
            assertTrue(app.isPresent());
            assertEquals(1, app.get().getId());
            assertEquals("App 1", app.get().getName());
        });

    }

}