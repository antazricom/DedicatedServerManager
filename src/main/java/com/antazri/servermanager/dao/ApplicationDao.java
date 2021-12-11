package com.antazri.servermanager.dao;

import com.antazri.servermanager.models.Application;

import java.util.Optional;
import java.util.Set;

public interface ApplicationDao {

    Optional<Application> findById(int id);

    Set<Application> findByName(String name);

    Set<Application> findAll();

    Application add(Application application);

    Application update(Application application);

    boolean delete(Application application);
}
