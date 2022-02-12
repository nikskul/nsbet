package com.project.nsbet.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Модель матча
 */
@Entity
@Table(name = "Matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "match_id")
    private Long id;

    private Date time;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Team> teams;

    public Match() {
        teams = new ArrayList<Team>();
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
     * @return Date
     */
    public Date getTime() {
        return time;
    }

    
    /** 
     * @param time Дата матча
     */
    public void setTime(Date time) {
        this.time = time;
    }

    
    /** 
     * @return List<Team>
     */
    public List<Team> getTeams() {
        return teams;
    }

    
    /** 
     * @param teams Команды
     */
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
