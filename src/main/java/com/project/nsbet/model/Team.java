package com.project.nsbet.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Модель спортивной команды
 */
@Entity
@Table(name = "Teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "team_id")
    private Long id;
    private String name;
    private Float rating;
    private Short win;
    private Short draw;
    private Short lose;

    @ManyToMany(mappedBy = "teams")
    private List<Result> results;

    @ManyToMany(mappedBy = "teams")
    private List<Match> matches;

    public Team(String name) {
        this.name = name;
        results = new ArrayList<Result>();
        matches = new ArrayList<Match>();
        rating = 0f;
        win = 0;
        draw = 0;
        lose = 0;
    }

    public Team() { 
        results = new ArrayList<Result>();
        matches = new ArrayList<Match>();
        rating = 0f;
        win = 0;
        draw = 0;
        lose = 0;
    }

    
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
     * @return String
     */
    public String getName() {
        return name;
    }

    
    /** 
     * @param name Название команды
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /** 
     * @return Float
     */
    public Float getRating() {
        return rating;
    }

    
    /** 
     * @param rating Рейтинг
     */
    public void setRating(Float rating) {
        this.rating = rating;
    }

    
    /** 
     * @return Short
     */
    public Short getWin() {
        return win;
    }

    
    /** 
     * @param win Победы
     */
    public void setWin(Short win) {
        this.win = win;
    }

    
    /** 
     * @return Short
     */
    public Short getDraw() {
        return draw;
    }

    
    /** 
     * @param draw Количество ничьих
     */
    public void setDraw(Short draw) {
        this.draw = draw;
    }

    
    /** 
     * @return Short
     */
    public Short getLose() {
        return lose;
    }

    
    /** 
     * @param lose Поражения
     */
    public void setLose(Short lose) {
        this.lose = lose;
    }

    
    /** 
     * @return List<Result>
     */
    public List<Result> getResults() {
        return results;
    }

    
    /** 
     * @param results Результаты
     */
    public void setResults(List<Result> results) {
        this.results = results;
    }

    
    /** 
     * @return List<Match>
     */
    public List<Match> getMatches() {
        return matches;
    }

    
    /** 
     * @param matches Матчи
     */
    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
}
