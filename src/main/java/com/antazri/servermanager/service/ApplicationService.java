package com.antazri.servermanager.service;

import com.antazri.servermanager.models.Application;

import java.util.Optional;
import java.util.Set;

public interface ApplicationService {

    Optional<Application> getById(int id);

    Set<Application> fetchByName(String name);

    Set<Application> fetchAllApplications();

    Application createApplication(String name);

    Application updateApplication(Application application, String name);

    boolean deleteApplication(Application application);

}
