package com.project.nsbet.repository;

import com.project.nsbet.model.Team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    public Team findByName(String name);
        
}
