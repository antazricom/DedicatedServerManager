package com.antazri.servermanager.security.salt.dao;

import com.antazri.servermanager.security.salt.model.PsswdSalt;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PsswdSaltDao extends MongoRepository<PsswdSalt, String> {

    @Query("{userId:'?0'}")
    Optional<PsswdSalt> findByUserId(int id);
}
