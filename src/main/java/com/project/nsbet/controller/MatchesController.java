package com.project.nsbet.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.project.nsbet.model.Match;
import com.project.nsbet.model.Team;
import com.project.nsbet.model.User;
import com.project.nsbet.service.MatchService;
import com.project.nsbet.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Сервлет для страницы матча
 */
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
     * @return String
     */
    @GetMapping("/matches")
    public String backtoMatch() {
        return "redirect:/";
    }

    
    /** 
     * Отображает страницу матча
     * @param id Идентификатор
     * @param model Страница матча
     * @return String
     */
    @GetMapping("matches/{id}")
    public String show(@PathVariable String id, Map<String, Object> model) {

        User currentUser = userService.getCurrentUser();

        if (currentUser != null)
            model.put("user", currentUser);

        Match match = matchService.findById(id);
        model.put("match", match);

        return "match";
    }

    
    /** 
     * Создает новый матч
     * @param time Время матча
     * @param teamName1 Название первой команды
     * @param teamName2 Название второй команды
     * @return String
     * @throws ParseException
     */
    @PostMapping("/matches")
    public String addMatch(String time, String teamName1, String teamName2) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        Date parsed = (Date)format.parse(time);

        matchService.addMatch(parsed, new Team(teamName1), new Team(teamName2));

        return "redirect:/";
    }
}
