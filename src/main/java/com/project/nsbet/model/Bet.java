package com.project.nsbet.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Модель ставки
 */
@Entity
@Table(name = "Bets")
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bet_id")
    private Long id;

    @Column(name = "bet_date")
    private Date betDate;

    @Column(name = "bet_value")
    private BigDecimal betValue;
    
    @Column(name = "bet_coefficient")
    private Float betCoefficient;
    
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "results_result_id")
    private Result result;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "teams_result_id")
    private Team team;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "users_user_id", nullable=false)
    private User user;

    public Bet() { }

    
    /** 
     * @return Long
     */
    public Long getId() {
        return id;
    }

    
    /** 
     * @param id Идентификатор
     */
    public void setId(Long id) {
        this.id = id;
    }

    
    /** 
     * @return Date
     */
    public Date getBetDate() {
        return betDate;
    }

    
    /** 
     * @param betDate Дата ставки
     */
    public void setBetDate(Date betDate) {
        this.betDate = betDate;
    }

    
    /** 
     * @return BigDecimal
     */
    public BigDecimal getBetValue() {
        return betValue;
    }

    
    /** 
     * @param betValue Размер ставки
     */
    public void setBetValue(BigDecimal betValue) {
        this.betValue = betValue;
    }

    
    /** 
     * @return Float
     */
    public Float getBetCoefficient() {
        return betCoefficient;
    }

    
    /** 
     * @param betCoefficient Коэффициент
     */
    public void setBetCoefficient(Float betCoefficient) {
        this.betCoefficient = betCoefficient;
    }

    
    /** 
     * @return Result
     */
    public Result getResult() {
        return result;
    }

    
    /** 
     * @param result Результат
     */
    public void setResult(Result result) {
        this.result = result;
    }

    
    /** 
     * @return Team
     */
    public Team getTeam() {
        return team;
    }

    
    /** 
     * @param team Команда на которую поставили
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    
    /** 
     * @return User
     */
    public User getUser() {
        return user;
    }

    
    /** 
     * @param user Владелец ставки
     */
    public void setUser(User user) {
        this.user = user;
    }
}
