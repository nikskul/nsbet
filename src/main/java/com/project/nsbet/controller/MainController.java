package com.project.nsbet.controller;

import java.util.Map;

import com.project.nsbet.model.User;
import com.project.nsbet.service.MatchService;
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

    @Autowired
    public MainController(UserService userService, MatchService matchService) {
        this.userService = userService;
        this.matchService = matchService;
    }

    /** 
     * Загружает главную страницу
     * @param model Представление страницы
     * @return String
     */
    @GetMapping("/")
    public String home(Map<String, Object> model) {

        User currentUser = userService.getCurrentUser();

        if (currentUser != null)
            model.put("user", currentUser);

        model.put("matches", matchService.getAllMatches());

        return "index";
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

        return "admin";
    }

}
