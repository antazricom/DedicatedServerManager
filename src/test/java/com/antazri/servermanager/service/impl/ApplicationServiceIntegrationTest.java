package com.antazri.servermanager.service.impl;

import com.antazri.servermanager.DedicatedServerManagerApplication;
import com.antazri.servermanager.models.AppStatus;
import com.antazri.servermanager.models.Application;
import com.antazri.servermanager.service.ApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        classes = {DedicatedServerManagerApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ApplicationServiceIntegrationTest {

    @Autowired
    private ApplicationService applicationService;

    @Test
    void whenGettingApplicationById_shouldReturnFullApplicationFromDB() {
        // Given
        int appId = 1;

        // When
        Optional<Application> app = applicationService.getById(appId);

        // Then
        assertAll(() -> {
            assertTrue(app.isPresent());
            assertEquals(1, app.get().getId());
            assertEquals("App 1", app.get().getName());
        });
    }

    @Test
    void whenGettingApplicationByIdWithWrongId_shouldReturnOptionalEmpty() {
        // Given
        int appId = 10;

        // When
        Optional<Application> app = applicationService.getById(appId);

        // Then
        assertTrue(app.isEmpty());
    }

    @Test
    void whenGettingApplicationByNameWithSamePart_shouldReturnSetOfApps() {
        // Given
        String name = "app";

        // When
        Set<Application> apps = applicationService.fetchByName(name);

        // Then
        assertEquals(3, apps.size());
    }

    @Test
    void whenGettingApplicationByName_shouldReturnOptionalOfApplication() {
        // Given
        String name = "App 1";

        // When
        Optional<Application> app = applicationService.getByName(name);

        // Then
        assertAll(() -> {
            assertTrue(app.isPresent());
            assertEquals("App 1", app.get().getName());
            assertEquals(1, app.get().getId());
        });
    }

    @Test
    void whenGettingApplicationByNameWithUnknownName_shouldReturnOptionalEmpty() {
        // Given
        String name = "application";

        // When
        Optional<Application> app = applicationService.getByName(name);

        // Then
        assertTrue(app.isEmpty());
    }

    @Test
    void whenGettingApplicationByNameWithFullName_shouldReturnSetOfAppsWithSingleElement() {
        // Given
        String name = "App 1";

        // When
        Set<Application> apps = applicationService.fetchByName(name);

        // Then
        assertAll(() -> {
            assertEquals(1, apps.size());
            assertEquals("App 1", ((Application) new ArrayList(apps).get(0)).getName());
        });
    }

    @Test
    void whenGettingAllApplications_shouldReturnAll3AppsAsList() {
        // When
        List<Application> apps = applicationService.fetchAllApplications();

        // Then
        assertEquals(3, apps.size());
    }

    @Test
    void whenGettingApplicationByStatusDISABLE_shouldReturnSetOf2Apps() {
        // Given
        String status = "DISABLE";

        // When
        Set<Application> apps = applicationService.fetchByStatus(status);

        // Then
        assertEquals(2, apps.size());
    }

    @Test
    void whenCreatingNewApplication_shouldReturnFullObjectWithIdAndCreatedTimestamp() {
        // Given
        String name = "App 404";

        // When
        Application application = applicationService.createApplication(name);

        // Then
        assertAll(() -> {
            assertEquals(4, application.getId());
            assertEquals(LocalDate.now(), application.getCreatedAt().toLocalDateTime().toLocalDate());
        });
    }

    @Test
    void whenUpdatingApplication_shouldReturnFullObject() {
        // When
        Optional<Application> application = applicationService.updateApplication(1, "App 1B", "PAUSED");

        // Then
        assertAll(() -> {
            assertTrue(application.isPresent());
            assertEquals("App 1B", application.get().getName());
            assertEquals(AppStatus.PAUSED, application.get().getStatus());
        });
    }

    @Test
    void whenRemovingApplication_shouldReturnTrue() {
        // When
        boolean remove = applicationService.deleteApplication(3);

        // Then
        assertAll(() -> {
            assertTrue(remove);
            assertTrue(applicationService.getById(3).isEmpty());
        });
    }
}