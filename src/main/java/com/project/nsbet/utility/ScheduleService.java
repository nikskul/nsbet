package com.project.nsbet.utility;

import com.project.nsbet.service.ResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Stack;

@Service
public class ScheduleService implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(InterruptedException.class);

    private final ResultService resultService;
    private final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();

    private final Stack<LocalDateTime> matchResultUpdateTimeSchedule = new Stack<>();

    public ScheduleService(ResultService resultService) {
        this.resultService = resultService;
        taskScheduler.initialize();
    }

    public void updateSchedule() {
        LocalDateTime nextMatchStartTime = matchResultUpdateTimeSchedule.peek();
        taskScheduler.schedule(this, Timestamp.valueOf(nextMatchStartTime));
        log.info("Schedule was updated !");
    }

    public void addNewTime(LocalDateTime matchStartTime) {
        matchResultUpdateTimeSchedule.add(matchStartTime);
        matchResultUpdateTimeSchedule.sort(LocalDateTime::compareTo);
        updateSchedule();
    }

    @Override
    public void run() {
        resultService.manageResultsAndCreditWinnings();
        log.info("Match results was generated!");
    }
}

