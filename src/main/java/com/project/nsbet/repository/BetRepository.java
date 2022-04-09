package com.project.nsbet.repository;

import com.project.nsbet.model.Bet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {
    List<Bet> findAllByMatchId(Long matchId);
}
