package com.project.nsbet.loader;

import com.project.nsbet.service.ResultService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EndedMatchResultLoader implements CommandLineRunner {

    private final ResultService resultService;

    public EndedMatchResultLoader(ResultService resultService) {
        this.resultService = resultService;
    }

    @Override
    public void run(String... args) throws Exception {
        resultService.manageResultsAndCreditWinnings();
    }
}
