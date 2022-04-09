package com.project.nsbet.controller;

import com.project.nsbet.model.Team;
import com.project.nsbet.model.User;
import com.project.nsbet.service.TeamService;
import com.project.nsbet.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    private final UserService userService;
    private final TeamService teamService;

    public AdminController(UserService userService, TeamService teamService) {
        this.userService = userService;
        this.teamService = teamService;
    }

    /**
     * Загружает страницу создания матча
     * @param model Представление страницы
     * @return String
     */
    @GetMapping("/admin")
    public String admin(Map<String, Object> model) {

        User currentUser = userService.getCurrentUser();

        if (currentUser != null)
            model.put("user", currentUser);

        List<Team> teams = teamService.findAll();
        model.put("teams", teams);

        return "admin";
    }
}
