package com.antazri.servermanager.dao;

import com.antazri.servermanager.models.AppStatus;
import com.antazri.servermanager.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public interface ApplicationDao extends JpaRepository<Application, Integer> {

    Optional<Application> findByName(String name);

    @Query("SELECT a FROM Application a WHERE lower(a.name) LIKE :name")
    Set<Application> findByNameContaining(String name);

    Set<Application> findByStatus(AppStatus status);

    Application updateStatus(AppStatus status, int id);
}
