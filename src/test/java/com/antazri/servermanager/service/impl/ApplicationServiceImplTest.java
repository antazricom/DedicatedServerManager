package com.antazri.servermanager.service.impl;

import com.antazri.servermanager.dao.ApplicationDao;
import com.antazri.servermanager.dao.impl.ApplicationDaoImpl;
import com.antazri.servermanager.models.Application;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;

class ApplicationServiceImplTest {


    private final ApplicationDao applicationDao = Mockito.mock(ApplicationDaoImpl.class);
    private final ApplicationServiceImpl applicationService = new ApplicationServiceImpl(applicationDao);

    @Test
    void whenDaoReturnEmptyWithId_shouldThrowException() {
        // When
        Mockito.when(applicationDao.findById(anyInt())).thenReturn(Optional.empty());

        // Then
        assertThrows(IllegalArgumentException.class, () -> applicationService.getById(0));
    }

    @Test
    void whenSavingNewAppWithEmptyName_shouldThrowException() {
        // Given
        String name = "    ";

        // Then
        assertThrows(IllegalArgumentException.class, () -> applicationService.createApplication(name));
    }

    @Test
    void whenUpdatingNewAppWithEmptyName_shouldThrowException() {
        // Given
        String name = "    ";

        // Then
        assertThrows(IllegalArgumentException.class, () -> applicationService.updateApplication(90, name));
    }

    @Test
    void whenUpdatingAndDaoReturnEmpty_shouldThrowException() {
        // When
        Mockito.when(applicationDao.findById(anyInt())).thenReturn(Optional.empty());

        // Then
        assertThrows(NoSuchElementException.class, () -> applicationService.updateApplication(1, "Test"));
    }

    @Test
    void whenDeletingAndDaoReturnEmpty_shouldThrowException() {
        // When
        Mockito.when(applicationDao.findById(anyInt())).thenReturn(Optional.empty());

        // Then
        assertThrows(NoSuchElementException.class, () -> applicationService.deleteApplication(1));
    }
}