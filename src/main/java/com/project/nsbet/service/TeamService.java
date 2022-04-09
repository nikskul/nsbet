package com.project.nsbet.service;

import com.project.nsbet.model.Match;
import com.project.nsbet.model.Result;
import com.project.nsbet.model.Team;
import com.project.nsbet.repository.TeamRepository;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Optional<Team> findById(Long id) {
        return teamRepository.findById(id);
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public List<Team> getAllTeamsSortedByName() {
        var teams = teamRepository.findAll();
        teams.sort(Comparator.comparing(Team::getName));
        return teams;
    }

    @Transactional
    public void registerTeam(String teamName) {
        Team team = new Team(teamName);
        teamRepository.saveAndFlush(team);
    }

    @Transactional
    public void updateTeamInfoFromEndedMatch(Match match) {

        Team firstTeam = match.getFirstTeam();
        Team secondTeam = match.getSecondTeam();

        int firstTeamScore = match.getResult().getFirstTeamScore();
        int secondTeamScore = match.getResult().getSecondTeamScore();

        if (firstTeamScore > secondTeamScore) {
            firstTeam.setWins(firstTeam.getWins() + 1);
            secondTeam.setLoses(secondTeam.getLoses() + 1);
        } else if (firstTeamScore == secondTeamScore) {
            firstTeam.setDraws(firstTeam.getDraws() + 1);
            secondTeam.setDraws(secondTeam.getDraws() + 1);
        } else {
            secondTeam.setWins(secondTeam.getWins() + 1);
            firstTeam.setLoses(firstTeam.getLoses() + 1);
        }

        teamRepository.save(firstTeam);
        teamRepository.save(secondTeam);
    }
}
