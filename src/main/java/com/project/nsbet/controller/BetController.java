package com.project.nsbet.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.project.nsbet.model.Bet;
import com.project.nsbet.model.Match;
import com.project.nsbet.model.User;
import com.project.nsbet.service.BetService;
import com.project.nsbet.service.MatchService;
import com.project.nsbet.service.ResultService;
import com.project.nsbet.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * Сервлет для ставок
 */
@Controller
public class BetController {

    private final UserService userService;
    private final MatchService matchService;
    private final BetService betService;
    public final ResultService resultService;

    @Autowired
    public BetController(UserService userService, MatchService matchService, BetService betService, ResultService resultService) {
        this.userService = userService;
        this.matchService = matchService;
        this.betService = betService;
        this.resultService = resultService;
    }

    /** 
     * Загружает страницу ставок
     * @param model Представление страницы
     * @return String
     */
    @GetMapping("/bets")
    public String show(Map<String, Object> model) {

        resultService.generateResults();
        
        User currentUser = userService.getCurrentUser();
        if (currentUser != null)
            model.put("user", currentUser);

        List<Bet> list = currentUser.getBets();
        Collections.reverse(list);

        model.put("bets", list);


        return "bets";
    }

    /** 
     * Создает ставку
     * @param matchId ИД матча
     * @param userChoice Выбор исхода
     * @param betValue Размер ставки
     * @return String
     */
    @PostMapping("/bets")
    public String addBet(String matchId, String userChoice, String betValue) {

        User currentUser = userService.getCurrentUser();
        Match match = matchService.findById(matchId);

        String betCoefficient;

        if (userChoice.equals("2"))
            betCoefficient = "1.25";
        else
            betCoefficient = "2";

        if (!betService.save(match, currentUser, userChoice, betValue, betCoefficient))
            return "redirect:/matches/" + match.getId() + "?error";

        return "redirect:/";
    }
}
