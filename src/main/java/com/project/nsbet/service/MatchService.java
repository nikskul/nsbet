package com.project.nsbet.service;


import com.project.nsbet.model.Match;
import com.project.nsbet.model.Team;
import com.project.nsbet.repository.MatchRepository;
import com.project.nsbet.repository.TeamRepository;
import com.project.nsbet.utility.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class MatchService {

    public final MatchRepository matchRepository;
    public final ScheduleService scheduleService;
    private final TeamRepository teamRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository,
                        ScheduleService scheduleService,
                        TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.scheduleService = scheduleService;
        this.teamRepository = teamRepository;
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
    public void registerMatch(LocalDateTime matchDateTime, String firstTeamName, String secondTeamName) throws Exception {

        if (matchDateTime.isBefore(LocalDateTime.now())) {
            throw new Exception("Время начала матча не может быть прошедшим.");
        }

        if (firstTeamName.equals(secondTeamName))
            throw new Exception("Имена команд не могут совпадать.");

        Team firstTeam = teamRepository
                .findByNameIgnoreCase(firstTeamName.trim())
                .orElse(new Team(firstTeamName));
        Team secondTeam = teamRepository
                .findByNameIgnoreCase(secondTeamName.trim())
                .orElse(new Team(secondTeamName));

        Match match = new Match();
        match.setMatchStartTime(matchDateTime);

        match.setFirstTeam(firstTeam);
        match.setSecondTeam(secondTeam);

        matchRepository.saveAndFlush(match);

        scheduleService.addNewTime(match.getMatchStartTime());
    }
}
