package com.antazri.servermanager.service;

import com.antazri.servermanager.models.Action;
import com.antazri.servermanager.models.ActionType;
import com.antazri.servermanager.models.Application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ActionService {

    Optional<Action> getById(int id);

    List<Action> fetchActionsByType(String type);

    List<Action> fetchActionsByApplication(int applicationId);

    List<Action> fetchActionsByApplicationAndCreationDate(int applicationId, LocalDateTime startDate, LocalDateTime endDate);

    List<Action> fetchActionsByCreationDate(LocalDateTime startDate, LocalDateTime endDate);

    List<Action> fetchAllActions();

    Action createAppAction(int appId, String type, String description);

    Action updateAppAction(int actionId, String type, String description);

    boolean removeAppAction(int actionId);
}
