package com.antazri.servermanager.service.impl;

import com.antazri.servermanager.dao.ApplicationDao;
import com.antazri.servermanager.models.AppStatus;
import com.antazri.servermanager.models.Application;
import com.antazri.servermanager.service.ApplicationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private static final Logger logger = LogManager.getLogger(ApplicationServiceImpl.class);

    private final ApplicationDao applicationDao;

    public ApplicationServiceImpl(ApplicationDao applicationDao) {
        this.applicationDao = applicationDao;
    }

    @Override
    public Optional<Application> getById(int id) {
        validRequestedId(id);

        return applicationDao.findById(id);
    }

    @Override
    public Set<Application> fetchByName(String name) {
        return applicationDao.findByName(name);
    }

    @Override
    public Set<Application> fetchByStatus(String status) {
        AppStatus appStatus = validateRequestedStatus(status);

        return applicationDao.findByStatus(appStatus);
    }

    @Override
    public List<Application> fetchAllApplications() {
        return applicationDao.findAll();
    }

    @Override
    public Application createApplication(String name) {
        validateRequestedName(name);

        return applicationDao.save(Application.create(name, AppStatus.DISABLE));
    }

    @Override
    public Optional<Application> updateApplication(int appId, String name, String status) {
        validRequestedId(appId);
        validateRequestedName(name);
        AppStatus appStatus = validateRequestedStatus(status);

        Application app = fetchApplication(applicationDao, appId);
        app.setName(name);
        app.setStatus(appStatus);

        return Optional.of(applicationDao.save(app));
    }

    @Override
    public Optional<Application> updateApplicationStatus(int appId, String status) {
        validRequestedId(appId);
        AppStatus appStatus = validateRequestedStatus(status);

        return Optional.ofNullable(applicationDao.updateStatus(appStatus, appId));
    }

    @Override
    public boolean deleteApplication(int appId) {
        Application app = fetchApplication(applicationDao, appId);
        applicationDao.delete(app);

        return true;
    }

    private Application fetchApplication(ApplicationDao applicationDao, int appId) {
        Application app = applicationDao.findById(appId).orElseThrow(() -> {
            logger.error("No Application found with id {}", appId);
            throw new NoSuchElementException("No Application found");
        });

        return app;
    }

    private void validateRequestedName(String name) {
        if (name.isBlank()) {
            logger.error("Name can't be blank or null");
            throw new IllegalArgumentException("Name can not be blank or null");
        }
    }

    private void validRequestedId(int id) {
        if (id <= 0) {
            logger.error("Id {} requested invalid", id);
            throw new IllegalArgumentException("ID invalid.");
        }
    }

    private AppStatus validateRequestedStatus(String status) {
        return Arrays.stream(AppStatus.values())
                .filter(s -> status.equalsIgnoreCase(s.name()))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("No AppStatus found with status {} requested", status);
                    throw new NoSuchElementException("No Status found");
                });
    }
}
