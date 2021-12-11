package com.antazri.servermanager.dao;

import com.antazri.servermanager.models.Action;
import com.antazri.servermanager.models.ActionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.security.Timestamp;
import java.util.List;

@Repository
public interface ActionDao extends JpaRepository<Action, Integer> {

    List<Action> findByType(@Param("type") ActionType type);

    List<Action> findByApplicationId(@Param("id") int applicationId);

    List<Action> findByCreatedAt(@Param("start") Timestamp start,
                                 @Param("end") Timestamp end);

    List<Action> findByApplicationAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(@Param("id") int appId,
                                               @Param("start") Timestamp startDate,
                                               @Param("end") Timestamp endDate);
}
