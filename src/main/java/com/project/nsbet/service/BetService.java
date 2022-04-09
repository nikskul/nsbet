package com.project.nsbet.service;

import com.project.nsbet.model.Bet;
import com.project.nsbet.model.Match;
import com.project.nsbet.model.User;
import com.project.nsbet.repository.BetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class BetService {

    private final BetRepository betRepository;

    @Autowired
    public BetService(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    private void validateBetValueOrThrowException(User user, String betValue) throws Exception {
        double value;
        try {
            value = Double.parseDouble(betValue);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (value > user.getWallet().getBalance().doubleValue()
                || value <= 0) {
            throw new Exception("Не достаточно средств. Или ставка не корректна");
        }
    }

    @Transactional
    public void registerBet(Match match,
                            User user,
                            String userChoice,
                            String betValue
    ) throws Exception {

        validateBetValueOrThrowException(user, betValue);

        Bet bet = new Bet();

        bet.setMatch(match);
        bet.setBetValue(BigDecimal.valueOf(Double.parseDouble(betValue)));
        bet.setBetCreationDate(match.getMatchStartTime());

        switch (userChoice) {
            case "firstTeamWin":
                bet.setTeam(match.getFirstTeam());
                bet.setBetCoefficient(2f);
                break;
            case "secondTeamWin":
                bet.setTeam(match.getSecondTeam());
                bet.setBetCoefficient(2f);
                break;
            case "draw":
            default:
                bet.setTeam(null);
                bet.setBetCoefficient(1.25f);
                break;
        }

        user.getBets().add(bet);
        user.getWallet().setBalance(
                user.getWallet().getBalance()
                        .subtract(
                                BigDecimal.valueOf(
                                        Double.parseDouble(betValue)
                                )
                        )
        );
        bet.setUser(user);

        betRepository.saveAndFlush(bet);
    }
}
