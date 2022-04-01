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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Short getWin() {
        return win;
    }

    public void setWin(Short win) {
        this.win = win;
    }

    public Short getDraw() {
        return draw;
    }

    public void setDraw(Short draw) {
        this.draw = draw;
    }

    public Short getLose() {
        return lose;
    }

    public void setLose(Short lose) {
        this.lose = lose;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
}
