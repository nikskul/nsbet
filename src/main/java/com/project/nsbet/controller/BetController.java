package com.project.nsbet.controller;

import com.project.nsbet.exception.BadRequestException;
import com.project.nsbet.exception.NotEnoughMoneyException;
import com.project.nsbet.exception.NotFoundException;
import com.project.nsbet.model.Bet;
import com.project.nsbet.model.Match;
import com.project.nsbet.model.User;
import com.project.nsbet.service.BetService;
import com.project.nsbet.service.MatchService;
import com.project.nsbet.service.TeamService;
import com.project.nsbet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public BetController(UserService userService, MatchService matchService, BetService betService, TeamService teamService) {
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
     * @param matchId    Матч
     * @param userChoice Выбор исхода
     * @param betValue   Размер ставки
     * @return String
     */
    @PostMapping("/bets")
    public String addBet(Model model,
                         Long matchId,
                         String userChoice,
                         String betValue
    ) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);

        try {
            betService.registerBet(matchId, currentUser, userChoice, betValue);
        } catch (NotFoundException e) {
            model.addAttribute("cause", e.getMessage());
            return "redirect:/404";
        } catch (BadRequestException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("match", matchService.findById(matchId).get());
            return "match";
        }

        return "redirect:/";
    }
}
