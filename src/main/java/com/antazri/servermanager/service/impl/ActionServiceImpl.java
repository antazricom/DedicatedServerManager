package com.antazri.servermanager.service.impl;

import com.antazri.servermanager.dao.ActionDao;
import com.antazri.servermanager.dao.ApplicationDao;
import com.antazri.servermanager.models.Action;
import com.antazri.servermanager.models.ActionType;
import com.antazri.servermanager.models.Application;
import com.antazri.servermanager.service.ActionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ActionServiceImpl implements ActionService {

    private static final Logger logger = LogManager.getLogger(ActionServiceImpl.class);

    private final ActionDao actionDao;

    @Qualifier("applicationDaoImpl")
    private final ApplicationDao applicationDao;

    public ActionServiceImpl(ActionDao actionDao, ApplicationDao applicationDao) {
        this.actionDao = actionDao;
        this.applicationDao = applicationDao;
    }

    @Override
    public Optional<Action> getById(int id) {
        validRequestedId(id);

        return actionDao.findById(id);
    }

    @Override
    public List<Action> fetchActionsByType(String type) {
        Optional<ActionType> typeOptional = fetchActionType(type);

        if (typeOptional.isEmpty()) {
            logger.error("Type {} not found", type);
            throw new NoSuchElementException("Type request not found");
        }

        return actionDao.findByType(typeOptional.get());
    }

    @Override
    public List<Action> fetchActionsByApplication(int applicationId) {
        validRequestedId(applicationId);

        return actionDao.findByApplicationId(applicationId);
    }

    @Override
    public List<Action> fetchActionsByApplicationAndCreationDate(int applicationId, LocalDateTime startDate, LocalDateTime endDate) {
        validRequestedId(applicationId);
        validRequestedDates(startDate, endDate);

        Application app = applicationDao.findById(applicationId).orElseThrow(() -> {
            logger.error("No Application found with id {}", applicationId);
            throw new NoSuchElementException("No application found");
        });

        return actionDao.findByApplicationAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(
                applicationId,
                Timestamp.valueOf(startDate),
                Timestamp.valueOf(endDate)
        );
    }

    @Override
    public List<Action> fetchActionsByCreationDate(LocalDateTime startDate, LocalDateTime endDate) {
        validRequestedDates(startDate, endDate);
        return actionDao.findByCreatedAt(Timestamp.valueOf(startDate), Timestamp.valueOf(endDate));
    }

    @Override
    public List<Action> fetchActionsByCreationDate(int timespan) {
        if (timespan == 0) {
            return Collections.emptyList();
        } else if (timespan < 0) {
            logger.error("Timespan can not be a negative number");
            throw new IllegalArgumentException("Timespan can not be a negative number");
        }

        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = LocalDateTime.now().minusDays(timespan);

        return actionDao.findByCreatedAt(Timestamp.valueOf(startDate), Timestamp.valueOf(endDate));
    }

    @Override
    public List<Action> fetchAllActions() {
        return actionDao.findAll(Sort.by(Sort.Order.asc("application_id")));
    }

    @Override
    public Action createAppAction(int appId, String type, String description) {
        Application app = fetchApplication(appId);

        Optional<ActionType> typeOptional = fetchActionType(type);
        ActionType actionType = typeOptional.orElseThrow(() -> new IllegalArgumentException("Action type set is not valid"));

        if (validRequestedDescription(description)) {
            return actionDao.save(Action.create(actionType, description, app));
        } else {
            logger.error("Description set for Action is not valid");
            throw new IllegalArgumentException("Description set for Action is not valid");
        }
    }

    private Application fetchApplication(int appId) {
        Application app = applicationDao.findById(appId).orElseThrow(() -> {
            logger.error("No Application found with id {}", appId);
            throw new NoSuchElementException("No application found");
        });
        return app;
    }

    @Override
    public Action updateAppAction(int actionId, String type, String description) {
        Action action = actionDao.findById(actionId).orElseThrow(() -> {
            logger.error("No Action found with id {}", actionId);
            throw new NoSuchElementException("No action found");
        });

        Optional<ActionType> updatedType = fetchActionType(type);
        ActionType actionType = updatedType.orElseThrow(() -> new IllegalArgumentException("ActionType sent is not valid"));
        action.setType(actionType);

        if (validRequestedDescription(description)) {
            action.setDescription(description);
        }

        return actionDao.save(action);
    }

    private boolean validRequestedDescription(String description) {
        return description != null && !description.isBlank();
    }

    private Optional<ActionType> fetchActionType(String type) {
        return Arrays.stream(ActionType.values())
                .filter(t -> type.equalsIgnoreCase(t.name()))
                .findFirst();
    }

    @Override
    public boolean removeAppAction(int actionId) {
        Action action = actionDao.findById(actionId).orElseThrow(() -> {
            logger.error("No Action found with id {}", actionId);
            throw new NoSuchElementException("No action found");
        });

        try {
            actionDao.delete(action);
        } catch (Exception e) {
            logger.error("Error while removing Action {}", action);
            return false;
        }

        return true;
    }

    private void validRequestedId(int id) {
        if (id <= 0) {
            logger.error("Id {} requested invalid", id);
            throw new IllegalArgumentException("ID invalid.");
        }
    }

    private void validRequestedDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            logger.error("StartDate {} is before EndDate {}", startDate, endDate);
            throw new IllegalArgumentException("StartDate can not be before EndDate");
        }
    }
}
