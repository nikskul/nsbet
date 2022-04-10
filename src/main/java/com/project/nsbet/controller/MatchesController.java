package com.project.nsbet.controller;

import com.project.nsbet.exception.NotFoundException;
import com.project.nsbet.model.Match;
import com.project.nsbet.model.User;
import com.project.nsbet.service.MatchService;
import com.project.nsbet.service.TeamService;
import com.project.nsbet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private final TeamService teamService;

    @Autowired
    public MatchesController(MatchService matchService,
                             UserService userService,
                             TeamService teamService
    ) {
        this.matchService = matchService;
        this.userService = userService;
        this.teamService = teamService;
    }

    @GetMapping("/matches")
    public String getPage() {
        // TODO: Redirect to 404
        return "redirect:/";
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

        try {
            Match match = matchService.findById(id)
                    .orElseThrow(
                            () -> new NotFoundException("Матч с id:" + id + " не существует.")
                    );
            model.put("match", match);
        } catch (NotFoundException e) {
            model.put("cause", e.getMessage());
            return "redirect:/404";
        }

        return "match";
    }

    /**
     * Создает новый матч
     *
     * @param matchDateTime  Время матча
     * @param firstTeamName  Первая команда
     * @param secondTeamName Второй команда
     * @return String
     */
    @PostMapping("/matches")
    public String addMatch(String matchDateTime, String firstTeamName, String secondTeamName, Model model) {
        LocalDateTime parsedMatchDateTime
                = LocalDateTime.parse(matchDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

        try {
            matchService.registerMatch(parsedMatchDateTime, firstTeamName, secondTeamName);
        } catch (Exception e) {
            var teams = teamService.findAll();
            model.addAttribute("addMatchError", e.getMessage());
            model.addAttribute("teams", teams);
            return "admin";
        }
        model.addAttribute("addMatchSuccess", "Матч был успешно создан");
        return "admin";
    }
}
