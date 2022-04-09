package com.project.nsbet.controller;

import com.project.nsbet.exception.NotFoundException;
import com.project.nsbet.model.Bet;
import com.project.nsbet.model.Match;
import com.project.nsbet.model.User;
import com.project.nsbet.service.BetService;
import com.project.nsbet.service.MatchService;
import com.project.nsbet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
public class BetController {

    private final UserService userService;
    private final MatchService matchService;
    private final BetService betService;

    @Autowired
    public BetController(UserService userService, MatchService matchService, BetService betService) {
        this.userService = userService;
        this.matchService = matchService;
        this.betService = betService;
    }

    /**
     * Загружает страницу ставок
     *
     * @param model Представление страницы
     * @return String
     */
    @GetMapping("/bets")
    public String show(Map<String, Object> model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            model.put("user", currentUser);
            List<Bet> userBets = currentUser.getBets();
            userBets.sort(Comparator.comparing(Bet::getBetCreationDate));
            model.put("bets", userBets);
            return "bets";
        } else {
            model.put("userNotFoundException", new NotFoundException("Требуется авторизация."));
            return "redirect:/";
        }
    }

    /**
     * Создает ставку
     *
     * @param matchId    ИД матча
     * @param userChoice Выбор исхода
     * @param betValue   Размер ставки
     * @return String
     */
    @PostMapping("/bets")
    public String addBet(Map<String, Object> model,
                         Long matchId,
                         String userChoice,
                         String betValue
    ) {
        User currentUser = userService.getCurrentUser();
        Match match;
        try {
            match = matchService.findById(matchId).orElseThrow(
                    () -> new NotFoundException("Матч с id:" + matchId + " не существует.")
            );
        } catch (NotFoundException e) {
            model.put("matchNotFoundException", e.getMessage());
            return "match";
        }

        try {
            betService.registerBet(match, currentUser, userChoice, betValue);
        } catch (NotFoundException e) {
            model.put("matchNotFoundException", e.getMessage());
            return "match";
        } catch (Exception e) {
            model.put("notValidData", e.getMessage());
            return "match";
        }

        return "redirect:/";
    }
}
