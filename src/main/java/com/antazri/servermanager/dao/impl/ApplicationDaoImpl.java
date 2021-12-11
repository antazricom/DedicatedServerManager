package com.antazri.servermanager.dao.impl;

import com.antazri.servermanager.dao.ApplicationDao;
import com.antazri.servermanager.models.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public class ApplicationDaoImpl implements ApplicationDao {

    private static final Logger logger = LogManager.getLogger(ApplicationDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Application> findById(int id) {
        return Optional.ofNullable(entityManager.find(Application.class, id));
    }

    @Override
    public Set<Application> findByName(String name) {
        Query query = entityManager.createNamedQuery("Application.FindByName");
        query.setParameter("name", name);
        return new LinkedHashSet(query.getResultList());
    }

    @Override
    public Set<Application> findAll() {
        Query query = entityManager.createNamedQuery("Application.FindAll");
        return new LinkedHashSet(query.getResultList());
    }

    @Override
    public Application save(Application application) {
        entityManager.persist(application);
        return application;
    }

    @Override
    public Application update(Application application) {
        return entityManager.merge(application);
    }

    @Override
    public boolean delete(Application application) {
        try {
            entityManager.remove(entityManager.contains(application)
                    ? application
                    : entityManager.merge(entityManager));

            return true;
        } catch (IllegalArgumentException exception) {
            logger.error("RuntimeException thrown while removing application {}", application);
            return false;
        } catch (Exception exception) {
            logger.error("Exception thrown while removing application {}", application);
            return false;
        }
    }
}
