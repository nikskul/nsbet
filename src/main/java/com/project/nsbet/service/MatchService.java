package com.project.nsbet.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.project.nsbet.model.Match;
import com.project.nsbet.model.Team;
import com.project.nsbet.repository.MatchRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с {@link Match}
 */
@Service
public class MatchService {
    
    public final MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    
    /** 
     * Возвращает список всех матчей
     * @return List<Match>
     */
    public  List<Match> getAllMatches() {
        List<Match> list = matchRepository.findAll().subList(0, matchRepository.findAll().size());
        
        Collections.reverse(list);

        return list;
    }

    
    /** 
     * Метод для создания нового матча
     * @param date Дата матча
     * @param team1 Команда 1
     * @param team2 Команда 2
     */
    public void addMatch(Date date, Team team1, Team team2) {
        Match match = new Match();

        team1.getMatches().add(match);

        team2.getMatches().add(match);

        List<Team> teams = new ArrayList<Team>();;
        teams.add(team1);
        teams.add(team2);

        match.setTime(date);
        match.setTeams(teams);

        matchRepository.save(match);
    }

    
    /** 
     * Возвращает {@link Match} с заданным идентификатором 
     * @param id Идентификатор матча
     * @return Match
     */
    public Match findById(String id) {
        return matchRepository.getById(Long.parseLong(id));
    }
}