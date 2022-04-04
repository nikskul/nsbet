package com.project.nsbet.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.project.nsbet.model.Bet;
import com.project.nsbet.model.Result;
import com.project.nsbet.model.Team;
import com.project.nsbet.model.Wallet;
import com.project.nsbet.repository.BetRepository;
import com.project.nsbet.repository.ResultRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с {@link Result}
 */
@Service
public class ResultService {

    private final BetRepository betRepository;
    private final ResultRepository resultRepository;

    @Autowired
    public ResultService(BetRepository betRepository, ResultRepository resultRepository) {
        this.betRepository = betRepository;
        this.resultRepository = resultRepository;
    }

    public void generateResults() {
        Random rnd = new Random();
        List<Bet> bets = betRepository.findAll();

        for (Bet bet : bets) {
            if (bet.getBetDate().before(Calendar.getInstance().getTime()) && bet.getResult().getName().equals("null")) {

                Long index = (long) (rnd.nextInt(3));
                Result res = resultRepository.getById(++index);
                bet.setResult(res);


                Team team = bet.getTeam();
                team.getResults().add(res);
                res.getTeams().add(team);

                switch (res.getName()) {
                    case "win":

                        BigDecimal betCoefficient = BigDecimal.valueOf(Double.valueOf(bet.getBetCoefficient()));
                        BigDecimal betValue = bet.getBetValue();
                        Wallet wallet = bet.getUser().getWallet();
                        
                        wallet.setBalance(wallet.getBalance().add(betValue.multiply(betCoefficient)));
                        team.setWin((short) (team.getWin() + 1));

                        break;
                    case "lose":
                        team.setLose((short) (team.getLose() + 1));
                        break;
                    case "draw":
                    default:
                        team.setDraw((short) (team.getDraw() + 1));
                        break;
                }

                betRepository.save(bet);
            }
        }
    }
}
