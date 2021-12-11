package com.antazri.servermanager.service.impl;

import com.antazri.servermanager.dao.ApplicationDao;
import com.antazri.servermanager.dao.impl.ApplicationDaoImpl;
import com.antazri.servermanager.models.Application;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationServiceImplTest {


    private final ApplicationDao applicationDao = Mockito.mock(ApplicationDaoImpl.class);
    private final ApplicationServiceImpl applicationService = new ApplicationServiceImpl(applicationDao);

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
        Application application = Application.from(1, "App", new ArrayList<>());

        // Then
        assertThrows(IllegalArgumentException.class, () -> applicationService.updateApplication(application, name));
    }
}