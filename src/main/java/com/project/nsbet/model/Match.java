package com.project.nsbet.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "match_id")
    private Long id;
    private LocalDateTime matchStartTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "first_team_id")
    private Team firstTeam;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "second_team_id")
    private Team secondTeam;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "results_result_id")
    private Result result;

    public Match() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getMatchStartTime() {
        return matchStartTime;
    }

    public void setMatchStartTime(LocalDateTime matchStartTime) {
        this.matchStartTime = matchStartTime;
    }

    public Team getFirstTeam() {
        return firstTeam;
    }

    public void setFirstTeam(Team firstTeam) {
        this.firstTeam = firstTeam;
    }

    public Team getSecondTeam() {
        return secondTeam;
    }

    public void setSecondTeam(Team secondTeam) {
        this.secondTeam = secondTeam;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
