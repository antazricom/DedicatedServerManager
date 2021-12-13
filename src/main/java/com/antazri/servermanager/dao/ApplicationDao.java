package com.antazri.servermanager.dao;

import com.antazri.servermanager.models.AppStatus;
import com.antazri.servermanager.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Set;

@Repository
@Transactional
public interface ApplicationDao extends JpaRepository<Application, Integer> {

    Set<Application> findByName(String name);

    Set<Application> findByStatus(AppStatus status);

    Application updateStatus(AppStatus status, int id);
}
