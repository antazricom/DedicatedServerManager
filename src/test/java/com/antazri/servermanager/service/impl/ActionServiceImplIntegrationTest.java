package com.antazri.servermanager.service.impl;

import com.antazri.servermanager.DedicatedServerManagerApplication;
import com.antazri.servermanager.models.Action;
import com.antazri.servermanager.models.ActionType;
import com.antazri.servermanager.service.ActionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        classes = {DedicatedServerManagerApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ActionServiceImplIntegrationTest {

    @Autowired
    private ActionService actionService;

    @Test
    void whenFindingWithKnownId_shouldReturnFullObject() {
        // Given
        int id = 1;

        // When
        Optional<Action> action = actionService.getById(id);

        // Then
        Assertions.assertAll(() -> {
            assertTrue(action.isPresent());
            assertEquals("Action Description 1", action.get().getDescription());
            assertEquals(ActionType.INSTALL, action.get().getType());
        });
    }

    @Test
    void whenFindingWithUnknownId_shouldReturnEmptyOptional() {
        // Given
        int id = 100;

        // When
        Optional<Action> action = actionService.getById(id);

        // Then
        assertTrue(action.isEmpty());
    }

    @Test
    void whenFetchingByType_shouldReturnAllActionsWithType() {
        // Given
        ActionType type = ActionType.RUNNING;

        // When
        List<Action> actions = actionService.fetchActionsByType(type.name());

        // Then
        assertAll(() -> {
            assertEquals(2, actions.size());
            assertEquals("Action Description 6", actions.get(0).getDescription());
            assertEquals("Action Description 7", actions.get(1).getDescription());
        });
    }

    @Test
    void whenFetchingByTypeWithNoInstancesInDb_shouldReturnEmptyCollection() {
        // Given
        ActionType type = ActionType.PAUSE;

        // When
        List<Action> actions = actionService.fetchActionsByType(type.name());

        // Then
        assertTrue(actions.isEmpty());
    }

    @Test
    void whenFetchingByApplication_shouldReturnAllInstancesLinkedToApp() {
        // Given
        int appId = 3;

        // When
        List<Action> actions = actionService.fetchActionsByApplication(appId);

        // Then
        assertAll(() -> {
            assertEquals(2, actions.size());
            assertEquals("Action Description 3", actions.get(0).getDescription());
            assertEquals(ActionType.INSTALL, actions.get(0).getType());
            assertEquals("Action Description 9", actions.get(1).getDescription());
            assertEquals(ActionType.REMOVE, actions.get(1).getType());
        });
    }

    @Test
    void whenFetchingByDate_shouldReturnAllEntitiesCreatedAtDate() {
        // Given
        int timespan = (int) LocalDate.of(2021, 12, 1).until(LocalDate.now(), ChronoUnit.DAYS);

        // When
        List<Action> actions = actionService.fetchActionsByCreationDate(timespan);

        // Then
        assertAll(() -> {
            assertEquals(9, actions.size());
            assertEquals("Action Description 1", actions.get(0).getDescription());
            assertEquals("Action Description 2", actions.get(1).getDescription());
            assertEquals("Action Description 3", actions.get(2).getDescription());
            assertEquals("Action Description 4", actions.get(3).getDescription());
            assertEquals("Action Description 5", actions.get(4).getDescription());
            assertEquals("Action Description 6", actions.get(5).getDescription());
            assertEquals("Action Description 7", actions.get(6).getDescription());
            assertEquals("Action Description 8", actions.get(7).getDescription());
            assertEquals("Action Description 9", actions.get(8).getDescription());
        });
    }

    @Test
    void whenFetchingByDates_shouldReturnCollectionsOfEntites() {
        // Given
        LocalDateTime start = LocalDateTime.of(2021, 12, 14, 0, 0, 1);
        LocalDateTime end = LocalDateTime.of(2021, 12, 15, 0, 0, 1);

        // When
        List<Action> actions = actionService.fetchActionsByCreationDate(start, end);

        // Then
        assertAll(() -> {
            assertEquals(3, actions.size());
            assertEquals("Action Description 7", actions.get(0).getDescription());
            assertEquals("Action Description 8", actions.get(1).getDescription());
            assertEquals("Action Description 9", actions.get(2).getDescription());
        });
    }

    @Test
    void whenFetchingByDatesAndAppId_shouldReturnCollectionsOfEntitesCreatedAtDateForApplication() {
        // Given
        int appId = 1;
        LocalDateTime start = LocalDateTime.of(2021, 12, 10, 0, 0, 1);
        LocalDateTime end = LocalDateTime.of(2021, 12, 15, 0, 0, 1);

        // When
        List<Action> actions = actionService.fetchActionsByApplicationAndCreationDate(appId, start, end);

        // Then
        assertAll(() -> {
            assertEquals(3, actions.size());
            assertEquals("Action Description 1", actions.get(0).getDescription());
            assertEquals(ActionType.INSTALL, actions.get(0).getType());
            assertEquals("Action Description 6", actions.get(1).getDescription());
            assertEquals(ActionType.RUNNING, actions.get(1).getType());
            assertEquals("Action Description 8", actions.get(2).getDescription());
            assertEquals(ActionType.STOP, actions.get(2).getType());
        });
    }

    @Test
    void whenCreateNewAction_shouldReturnWithId() {
        // When
        Action action = actionService.createAppAction(1, ActionType.TEST.name(), "Action 10 to test");

        // Then
        assertAll(() -> {
            assertEquals(10, action.getId());
            assertTrue(actionService.getById(10).isPresent());
            assertEquals("Action 10 to test", actionService.getById(10).get().getDescription());
        });
    }

    @Test
    void whenUpdatingActionWithNewDescription_shouldReturnObjectWithChangedFields() {
        // When
        Action action = actionService.updateAppAction(2, ActionType.INSTALL.name(), "Action 2 UPDATED");

        // Then
        assertAll(() -> {
            assertTrue(actionService.getById(2).isPresent());
            assertEquals("Action 2 UPDATED", actionService.getById(2).get().getDescription());
        });
    }

    @Test
    void whenRemovingAction_shouldDeleteFromDb() {
        // Given
        int actionId = 4;

        // When
        actionService.removeAppAction(actionId);

        // Then
        assertTrue(actionService.getById(4).isEmpty());
    }
}