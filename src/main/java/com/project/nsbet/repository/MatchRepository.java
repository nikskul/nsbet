package com.project.nsbet.repository;

import com.project.nsbet.model.Match;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
    
}
