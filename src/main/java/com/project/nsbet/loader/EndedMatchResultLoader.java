package com.project.nsbet.loader;

import com.project.nsbet.model.Match;
import com.project.nsbet.service.MatchService;
import com.project.nsbet.service.ResultService;
import com.project.nsbet.utility.ScheduleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EndedMatchResultLoader implements CommandLineRunner {

    private final ResultService resultService;
    private final ScheduleService scheduleService;
    private final MatchService matchService;

    public EndedMatchResultLoader(ResultService resultService,
                                  ScheduleService scheduleService,
                                  MatchService matchService) {
        this.resultService = resultService;
        this.scheduleService = scheduleService;
        this.matchService = matchService;
    }

    @Override
    public void run(String... args) throws Exception {
        resultService.manageResultsAndCreditWinnings();

        var matches = matchService.findAll();
        matches.stream()
                .map(Match::getMatchStartTime)
                .filter(
                        matchStartTime -> matchStartTime.isAfter(LocalDateTime.now())
                )
                .forEach(scheduleService::addNewTime);
    }
}
