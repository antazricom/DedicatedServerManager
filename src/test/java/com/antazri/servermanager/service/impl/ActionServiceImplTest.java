package com.antazri.servermanager.service.impl;

import com.antazri.servermanager.dao.ActionDao;
import com.antazri.servermanager.dao.ApplicationDao;
import com.antazri.servermanager.models.Action;
import com.antazri.servermanager.models.ActionType;
import com.antazri.servermanager.models.Application;
import com.antazri.servermanager.service.ActionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ActionServiceImplTest {

    private final ActionDao actionDao = mock(ActionDao.class);
    private final ApplicationDao applicationDao = mock(ApplicationDao.class);
    private final ActionService actionService = new ActionServiceImpl(actionDao, applicationDao);

    @Test
    void whenGettingWithInvalidId_shouldThrowException() {
        // Then
        assertThrows(IllegalArgumentException.class, () -> actionService.getById(0));
    }

    @Test
    void whenFetchingByTypeWithWrongType_shouldThrowException() {
        // Then
        assertThrows(NoSuchElementException.class, () -> actionService.fetchActionsByType("Wrong Type"));
    }

    @Test
    void whenFetchingByApplicationWithInvalidId_shouldThrowException() {
        // Then
        assertThrows(IllegalArgumentException.class, () -> actionService.fetchActionsByApplication(0));
    }

    @Test
    void whenByAppAndDateWithInvalidId_shouldThrowException() {
        // When
        LocalDateTime start = LocalDateTime.of(2021, 12, 1, 0, 0, 1);
        LocalDateTime end = LocalDateTime.of(2021, 12, 12, 23, 59, 59);
        int appId = 0;

        // Then
        assertThrows(IllegalArgumentException.class, () -> actionService.fetchActionsByApplicationAndCreationDate(appId, start, end));
    }

    @Test
    void whenFetchingByAppAndDateWithStartDateAfterEndDate_shouldThrowException() {
        // When
        LocalDateTime start = LocalDateTime.of(2021, 12, 10, 0, 0, 1);
        LocalDateTime end = LocalDateTime.of(2021, 12, 2, 23, 59, 59);
        int appId = 1;

        // Then
        assertThrows(IllegalArgumentException.class, () -> actionService.fetchActionsByApplicationAndCreationDate(appId, start, end));
    }

    @Test
    void whenFetchingByAppAndDateWithStartDateAfterEndDateReturnNoApp_shouldThrowException() {
        // Given
        LocalDateTime start = LocalDateTime.of(2021, 12, 1, 0, 0, 1);
        LocalDateTime end = LocalDateTime.of(2021, 12, 12, 23, 59, 59);
        int appId = 1;

        // When
        when(applicationDao.findById(anyInt())).thenReturn(Optional.empty());

        // Then
        assertThrows(NoSuchElementException.class, () -> actionService.fetchActionsByApplicationAndCreationDate(appId, start, end));
    }

    @Test
    void whenFetchingDateWithStartDateAfterEndDate_shouldThrowException() {
        // When
        LocalDateTime start = LocalDateTime.of(2021, 12, 10, 0, 0, 1);
        LocalDateTime end = LocalDateTime.of(2021, 12, 2, 23, 59, 59);

        // Then
        assertThrows(IllegalArgumentException.class, () -> actionService.fetchActionsByCreationDate(start, end));
    }

    @Test
    void whenFetchingByDateWithWrongTimespan_shouldThrowException() {
        // When
        int timespan = -1;

        // Then
        assertThrows(IllegalArgumentException.class, () -> actionService.fetchActionsByCreationDate(timespan));
    }

    @Test
    void whenFetchingByDateWithTimespanAt0_shouldReturnEmptyList() {
        // Given
        int timespan = 0;

        // When
        List<Action> actions = actionService.fetchActionsByCreationDate(timespan);

        // Then
        assertTrue(actions.isEmpty());
    }

    @Test
    void whenCreatingActionWithNoApplicationWithID_shouldThrowException() {
        // Given
        int appId = 1;
        String type = "INSTALL";
        String description = "description";

        // When
        when(applicationDao.findById(anyInt())).thenReturn(Optional.empty());

        // Then
        assertThrows(NoSuchElementException.class, () -> actionService.createAppAction(appId, type, description));
    }

    @Test
    void whenCreatingActionWithUnknownType_shouldThrowException() {
        // Given
        int appId = 1;
        String type = "WRONG TEST";
        String description = "description";

        // When
        when(applicationDao.findById(anyInt())).thenReturn(Optional.of(Application.from(1, "test", Collections.emptyList())));

        // Then
        assertThrows(IllegalArgumentException.class, () -> actionService.createAppAction(appId, type, description));
    }

    @Test
    void whenCreatingActionWithNullDescription_shouldThrowException() {
        // Given
        int appId = 1;
        String type = "INSTALL";
        String description = null;

        // When
        when(applicationDao.findById(anyInt())).thenReturn(Optional.of(Application.from(1, "test", Collections.emptyList())));

        // Then
        assertThrows(IllegalArgumentException.class, () -> actionService.createAppAction(appId, type, description));
    }

    @Test
    void whenCreatingActionWithEmptyDescription_shouldThrowException() {
        // Given
        int appId = 1;
        String type = "INSTALL";
        String description = "    ";

        // When
        when(applicationDao.findById(anyInt())).thenReturn(Optional.of(Application.from(1, "test", Collections.emptyList())));

        // Then
        assertThrows(IllegalArgumentException.class, () -> actionService.createAppAction(appId, type, description));
    }

    @Test
    void whenUpdatingActionWithUnknownId_shouldThrowException() {
        // Given
        int actionId = 1;
        String type = "INSTALL";
        String description = "    ";

        // When
        when(actionDao.findById(anyInt())).thenReturn(Optional.empty());

        // Then
        assertThrows(NoSuchElementException.class, () -> actionService.updateAppAction(actionId, type, description));
    }

    @Test
    void whenUpdatingActionWithWrongType_shouldThrowException() {
        // Given
        int actionId = 1;
        String type = "WRONG TEST";
        String description = "description";

        // When
        when(actionDao.findById(anyInt())).thenReturn(Optional.of(Action.from(actionId, ActionType.TEST, description, Application.create("test"))));

        // Then
        assertThrows(IllegalArgumentException.class, () -> actionService.updateAppAction(actionId, type, description));
    }

    @Test
    void whenRemovingActionWithIDNotFound_shouldThrowException() {
        // Given
        int actionId = 1;

        // When
        when(actionDao.findById(anyInt())).thenReturn(Optional.empty());

        // Then
        assertThrows(NoSuchElementException.class, () -> actionService.removeAppAction(actionId));
    }
}