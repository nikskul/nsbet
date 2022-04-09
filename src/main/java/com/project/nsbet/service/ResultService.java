package com.project.nsbet.service;

import com.project.nsbet.model.*;
import com.project.nsbet.repository.BetRepository;
import com.project.nsbet.repository.MatchRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ResultService {

    private final MatchRepository matchRepository;
    private final BetRepository betRepository;

    public ResultService(MatchRepository matchRepository,
                         BetRepository betRepository
    ) {
        this.matchRepository = matchRepository;
        this.betRepository = betRepository;
    }

    private Optional<Team> getMatchWinnerTeam(Match match) {
        int firstTeamScore = match.getResult().getFirstTeamScore();
        int secondTeamScore = match.getResult().getSecondTeamScore();

        return firstTeamScore > secondTeamScore
                ? Optional.ofNullable(match.getFirstTeam())
                : firstTeamScore < secondTeamScore
                ? Optional.ofNullable(match.getSecondTeam())
                : Optional.empty();
    }

    private void creditUserWinning(Bet bet) {
        BigDecimal betCoefficient = BigDecimal.valueOf(Double.valueOf(bet.getBetCoefficient()));
        Wallet userWallet = bet.getUser().getWallet();
        userWallet.setBalance(
                userWallet
                        .getBalance()
                        .add(bet.getBetValue().multiply(betCoefficient)
                        )
        );

        bet.setBetWin(true);
        betRepository.save(bet);
    }

    private void updateMatchBetting(Match match) {

        Optional<Team> winnerTeam = getMatchWinnerTeam(match);

        List<Bet> currentMatchBets = betRepository.findAllByMatchId(match.getId());

        List<Bet> winningBets = currentMatchBets
                .stream()
                .filter(bet -> Objects.equals(bet.getTeam(), winnerTeam.orElse(null)))
                .collect(Collectors.toList());

        winningBets.forEach(this::creditUserWinning);

        List<Bet> loserBets = currentMatchBets
                .stream()
                .filter(bet -> !winningBets.contains(bet))
                .collect(Collectors.toList());

        loserBets.forEach(bet -> {
            bet.setBetWin(false);
            betRepository.save(bet);
        });
    }

    private void generateMatchResultsAndSave(Match match) {
        Random rnd = new Random();
        Result matchResult = new Result();
        matchResult.setFirstTeamScore(rnd.nextInt(5));
        matchResult.setSecondTeamScore(rnd.nextInt(5));

        match.setResult(matchResult);
        matchRepository.save(match);
    }

    @Transactional
    public void manageResultsAndCreditWinnings() {
        List<Match> alreadyStartedAndWithoutResultMatches = matchRepository.findAll()
                .stream()
                .filter(match -> match.getMatchStartTime().isBefore(LocalDateTime.now()))
                .filter(match -> match.getResult() == null)
                .collect(Collectors.toList());
        for (Match match : alreadyStartedAndWithoutResultMatches) {
            generateMatchResultsAndSave(match);
            updateMatchBetting(match);
        }
    }
}
