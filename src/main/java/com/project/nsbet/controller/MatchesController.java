package com.project.nsbet.controller;

import com.project.nsbet.model.Team;
import com.project.nsbet.model.User;
import com.project.nsbet.service.MatchService;
import com.project.nsbet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Controller
public class MatchesController {

    private final MatchService matchService;
    private final UserService userService;

    @Autowired
    public MatchesController(MatchService matchService, UserService userService) {
        this.matchService = matchService;
        this.userService = userService;
    }

    /**
     * Отображает страницу матча
     *
     * @param id    Идентификатор
     * @param model Страница матча
     * @return String
     */
    @GetMapping("matches/{id}")
    public String show(@PathVariable(name = "id") Long id, Map<String, Object> model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null)
            model.put("user", currentUser);
        matchService.findById(id)
                .ifPresent(match -> model.put("match", match));
        return "match";
    }

    /**
     * Создает новый матч
     *
     * @param matchDateTime     Время матча
     * @param firstTeam         Первая команда
     * @param secondTeam        Второй команда
     * @return String
     */
    @PostMapping("/matches")
    public String addMatch(String matchDateTime, Team firstTeam, Team secondTeam) {
        LocalDateTime parsedMatchDateTime
                = LocalDateTime.parse(matchDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        matchService.registerMatch(parsedMatchDateTime, firstTeam, secondTeam);
        return "redirect:/";
    }
}
