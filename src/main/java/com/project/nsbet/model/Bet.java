package com.project.nsbet.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Bets")
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bet_id")
    private Long id;
    private Boolean isBetWin;

    @Column(name = "bet_date")
    private LocalDateTime betCreationDate;

    @Column(name = "bet_value")
    private BigDecimal betValue;
    
    @Column(name = "bet_coefficient")
    private Float betCoefficient;
    
    @OneToOne
    @JoinColumn(name = "matches_match_id")
    private Match match;

    @OneToOne
    @JoinColumn(name = "teams_result_id")
    private Team team;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "users_user_id")
    private User user;

    public Bet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getBetWin() {
        return isBetWin;
    }

    public void setBetWin(Boolean betWin) {
        isBetWin = betWin;
    }

    public LocalDateTime getBetCreationDate() {
        return betCreationDate;
    }

    public void setBetCreationDate(LocalDateTime betCreationDate) {
        this.betCreationDate = betCreationDate;
    }

    public BigDecimal getBetValue() {
        return betValue;
    }

    public void setBetValue(BigDecimal betValue) {
        this.betValue = betValue;
    }

    public Float getBetCoefficient() {
        return betCoefficient;
    }

    public void setBetCoefficient(Float betCoefficient) {
        this.betCoefficient = betCoefficient;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
