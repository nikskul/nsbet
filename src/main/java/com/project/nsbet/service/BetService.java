package com.project.nsbet.service;

import com.project.nsbet.exception.BadRequestException;
import com.project.nsbet.exception.NotEnoughMoneyException;
import com.project.nsbet.exception.NotFoundException;
import com.project.nsbet.model.Bet;
import com.project.nsbet.model.Match;
import com.project.nsbet.model.User;
import com.project.nsbet.repository.BetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class BetService {

    private final BetRepository betRepository;

    private final MatchService matchService;

    @Autowired
    public BetService(BetRepository betRepository, MatchService matchService) {
        this.betRepository = betRepository;
        this.matchService = matchService;
    }

    private void validateBetValueOrThrowException(User user, String betValue) throws NotEnoughMoneyException {
        double value;

        value = Double.parseDouble(betValue);

        if (value > user.getWallet().getBalance().doubleValue()
                || value <= 0) {
            throw new NotEnoughMoneyException("Не достаточно средств. Или ставка не корректна");
        }
    }

    @Transactional
    public void registerBet(Long matchId,
                            User user,
                            String userChoice,
                            String betValue
    ) throws NotFoundException, BadRequestException {

        Match match = matchService.findById(matchId).orElseThrow(
                () -> new NotFoundException("Матч с id:" + matchId + " не существует.")
        );

        if (match.getMatchStartTime().isBefore(LocalDateTime.now()))
            throw new BadRequestException("Матч уже начался.");

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
