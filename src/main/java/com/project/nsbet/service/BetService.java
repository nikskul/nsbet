package com.project.nsbet.service;

import java.math.BigDecimal;

import com.project.nsbet.model.Bet;
import com.project.nsbet.model.Match;
import com.project.nsbet.model.Team;
import com.project.nsbet.model.User;
import com.project.nsbet.repository.BetRepository;
import com.project.nsbet.repository.ResultRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с {@link Match}
 */
@Service
public class BetService {

    private final BetRepository betRepository;
    private final ResultRepository resultRepository;

    @Autowired
    public BetService(BetRepository betRepository, ResultRepository resultRepository) {
        this.betRepository = betRepository;
        this.resultRepository = resultRepository;
    }

    
    /** 
     * Создает новую ставку и сохраняет ее в БД
     * @param match Матч для ставки
     * @param user Пользователь который ставит
     * @param userChoice Выбор пользователя
     * @param betValue Размер ставки
     * @param betCoefficient Коэффициент ставки
     * @return boolean
     */
    public boolean save(Match match, User user, String userChoice, String betValue, String betCoefficient) {

        try {
            Float.parseFloat(betValue);
        } catch (Exception e) {
            return false;
        }

        if (Float.parseFloat(betValue) > user.getWallet().getBalance().floatValue()
                || Float.parseFloat(betValue) <= 0) {
            return false;
        }

        Bet bet = new Bet();

        bet.setUser(user);
        bet.setBetValue(BigDecimal.valueOf(Double.parseDouble(betValue)));
        bet.setBetCoefficient(Float.parseFloat(betCoefficient));
        bet.setResult(resultRepository.getById(4L));
        bet.setBetDate(match.getTime());

        switch (userChoice) {
            case "1":
                bet.setTeam(match.getTeams().get(0));
                break;
            case "3":
                bet.setTeam(match.getTeams().get(1));
                break;
            case "2":
            default:
                bet.setTeam(new Team());
                break;
        }

        user.getBets().add(bet);
        user.getWallet().setBalance(user.getWallet().getBalance().subtract(BigDecimal.valueOf(Double.parseDouble(betValue))));

        betRepository.save(bet);
        return true;
    }
}
