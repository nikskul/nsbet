package com.project.nsbet.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.project.nsbet.model.Match;
import com.project.nsbet.model.Team;
import com.project.nsbet.model.User;
import com.project.nsbet.service.MatchService;
import com.project.nsbet.service.TeamService;
import com.project.nsbet.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Сервлет для главной страницы и страницы настроек
 */
@Controller
public class MainController {

    private final UserService userService;
    private final MatchService matchService;
    private final TeamService teamService;

    @Autowired
    public MainController(UserService userService, MatchService matchService, TeamService teamService) {
        this.userService = userService;
        this.matchService = matchService;
        this.teamService = teamService;
    }

    /** 
     * Загружает главную страницу
     * @param model Представление страницы
     * @return String
     */
    @GetMapping("/")
    public String getMainPageAndGenerateMatchResults(Map<String, Object> model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null)
            model.put("user", currentUser);

        var matches = matchService.getAllMatchesSortedByTime();
        model.put("matches", matches);

        return "index";
    }

}
