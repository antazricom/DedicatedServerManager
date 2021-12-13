package com.antazri.servermanager.service;

import com.antazri.servermanager.models.Application;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ApplicationService {

    Optional<Application> getById(int id);

    Set<Application> fetchByName(String name);

    Set<Application> fetchByStatus(String status);

    List<Application> fetchAllApplications();

    Application createApplication(String name);

    Optional<Application> updateApplication(int appId, String name, String status);

    Optional<Application> updateApplicationStatus(int appId, String status);

    boolean deleteApplication(int appId);

}
