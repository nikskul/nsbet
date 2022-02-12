package com.project.nsbet.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Модель результата
 */
@Entity
@Table(name = "Results")
public class Result {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "result_id")
    private Long id;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Team> teams;

    public Result(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Result () { 
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
     * @return String
     */
    public String getName() {
        return name;
    }

    
    /** 
     * @param name Название исхода
     */
    public void setName(String name) {
        this.name = name;
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
