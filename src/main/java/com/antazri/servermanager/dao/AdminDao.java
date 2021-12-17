package com.antazri.servermanager.dao;

import com.antazri.servermanager.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminDao extends JpaRepository<Admin, Integer> {

    Optional<Admin> findByUsername(String username);
}
