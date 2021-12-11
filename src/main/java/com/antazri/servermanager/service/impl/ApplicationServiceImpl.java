package com.antazri.servermanager.service.impl;

import com.antazri.servermanager.dao.ApplicationDao;
import com.antazri.servermanager.models.Application;
import com.antazri.servermanager.service.ApplicationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private static final Logger logger = LogManager.getLogger(ApplicationServiceImpl.class);

    private final ApplicationDao applicationDao;

    public ApplicationServiceImpl(ApplicationDao applicationDao) {
        this.applicationDao = applicationDao;
    }

    @Override
    public Optional<Application> getById(int id) {
        if (id < 0) {
            logger.error("id {} not valid", id);
            return Optional.empty();
        }

        return applicationDao.findById(id);
    }

    @Override
    public Set<Application> fetchByName(String name) {
        return applicationDao.findByName(name);
    }

    @Override
    public Set<Application> fetchAllApplications() {
        return applicationDao.findAll();
    }

    @Override
    public Application createApplication(String name) {
        validateRequestedName(name);

        return applicationDao.save(Application.create(name));
    }

    @Override
    public Application updateApplication(Application application, String name) {
        validateRequestedName(name);
        application.setName(name);

        return applicationDao.update(application);
    }

    private void validateRequestedName(String name) {
        if (name.isBlank()) {
            logger.error("Name can't be blank or null");
            throw new IllegalArgumentException("Name can not be blank or null");
        }
    }

    @Override
    public boolean deleteApplication(Application application) {
        return applicationDao.delete(application);
    }
}
