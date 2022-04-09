package com.project.nsbet.service;


import java.time.LocalDateTime;
import java.util.*;

import com.project.nsbet.model.Match;
import com.project.nsbet.model.Team;
import com.project.nsbet.repository.MatchRepository;

import com.project.nsbet.utility.ScheduleService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MatchService {
    
    public final MatchRepository matchRepository;
    public final ScheduleService scheduleService;

    @Autowired
    public MatchService(MatchRepository matchRepository, ScheduleService scheduleService) {
        this.matchRepository = matchRepository;
        this.scheduleService = scheduleService;
    }

    public Optional<Match> findById(Long id) {
        return matchRepository.findById(id);
    }

    public List<Match> findAll() {
        return matchRepository.findAll();
    }

    public List<Match> getAllMatchesSortedByTime() {
        var matches = matchRepository.findAll();
        matches.sort(Comparator.comparing(Match::getMatchStartTime));
        return matches;
    }

    public List<Team> getMatchTeams(Match match) {
        return List.of(match.getFirstTeam(), match.getSecondTeam());
    }

    @Transactional
    public void registerMatch(LocalDateTime matchDateTime, Team firstTeam, Team secondTeam) {
        Match match = new Match();
        match.setMatchStartTime(matchDateTime);

        match.setFirstTeam(firstTeam);
        match.setSecondTeam(secondTeam);

        matchRepository.saveAndFlush(match);

        scheduleService.addNewTime(match.getMatchStartTime());
    }
}
