package com.project.nsbet.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

/**
 * Модель роли в системе
 */
@Entity
@Table(name = "Roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Long id;
    private String name;
    
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() { }

    public Role(String name) {
        this.name = name;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    
    /** 
     * @return Long
     */
    public Long getId() {
        return id;
    }

    
    /** 
     * @param id Идентификатор роли
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
     * @param name Название роли
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /** 
     * @return Set<User>
     */
    public Set<User> getUsers() {
        return users;
    }

    
    /** 
     * @param users Список пользователей
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    
    /** 
     * @return String
     */
    @Override
    public String getAuthority() {
        return getName();
    }    
}
