package com.antazri.servermanager.service.impl;

import com.antazri.servermanager.dao.ApplicationDao;
import com.antazri.servermanager.models.AppStatus;
import com.antazri.servermanager.models.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(SpringExtension.class)
class ApplicationServiceImplTest {


    private final ApplicationDao applicationDao = Mockito.mock(ApplicationDao.class);
    private final ApplicationServiceImpl applicationServiceImpl = new ApplicationServiceImpl(applicationDao);

    @Test
    void whenDaoReturnEmptyWithId_shouldThrowException() {
        // When
        Mockito.when(applicationDao.findById(anyInt())).thenReturn(Optional.empty());

        // Then
        assertThrows(IllegalArgumentException.class, () -> applicationServiceImpl.getById(0));
    }

    @Test
    void whenSavingNewAppWithEmptyName_shouldThrowException() {
        // Given
        String name = "    ";

        // Then
        assertThrows(IllegalArgumentException.class, () -> applicationServiceImpl.createApplication(name));
    }

    @Test
    void whenUpdatingNewAppWithEmptyName_shouldThrowException() {
        // Given
        String name = "    ";
        String status = "DISABLE";

        // Then
        assertThrows(IllegalArgumentException.class, () -> applicationServiceImpl.updateApplication(90, name, status));
    }

    @Test
    void whenUpdatingAndDaoReturnEmpty_shouldThrowException() {
        // When
        Mockito.when(applicationDao.findById(anyInt())).thenReturn(Optional.empty());

        // Then
        assertThrows(NoSuchElementException.class, () -> applicationServiceImpl.updateApplication(1, "Test", "Test"));
    }

    @Test
    void whenDeletingAndDaoReturnEmpty_shouldThrowException() {
        // When
        Mockito.when(applicationDao.findById(anyInt())).thenReturn(Optional.empty());

        // Then
        assertThrows(NoSuchElementException.class, () -> applicationServiceImpl.deleteApplication(1));
    }

    @Test
    void whenUpdatingAppStatusWithWrongApp_shouldThrowException() {
        // When
        Mockito.when(applicationDao.findById(anyInt())).thenReturn(Optional.empty());
        String status = "WRONG STATUS";

        // Then
        assertThrows(NoSuchElementException.class, () -> applicationServiceImpl.updateApplicationStatus(1, status));
    }

    @Test
    void whenUpdatingAppStatusWithWrongStatusName_shouldThrowException() {
        // When
        Mockito.when(applicationDao.findById(anyInt())).thenReturn(Optional.of(Application.create("test", AppStatus.DISABLE)));
        String status = "WRONG STATUS";

        // Then
        assertThrows(NoSuchElementException.class, () -> applicationServiceImpl.updateApplicationStatus(1, status));
    }
}