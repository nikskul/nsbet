package com.project.nsbet.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Модель пользователя
 */
@Entity
@Table(name = "Users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String username;
    private String password;
    private Date birthday;
    private boolean active;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "avatars_avatar_id")
    private Avatar avatar;
    
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "wallets_wallet_id")
    private Wallet wallet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Bet> bets;

    public User(String firstName, String lastName, String username, String password, Date birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.birthday = birthday;
    }

    public User() { 
        bets = new ArrayList<Bet>();
    }

    
    /** 
     * @return Collection<? extends GrantedAuthority>
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    
    /** 
     * @return boolean
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    
    /** 
     * @return boolean
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    
    /** 
     * @return boolean
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    
    /** 
     * @return boolean
     */
    @Override
    public boolean isEnabled() {
        return isActive();
    }

    
    /** 
     * @return boolean
     */
    public boolean isActive() {
        return getActive();
    }

    
    /** 
     * @return String
     */
    @Override
    public String getUsername() {
        return username;
    }

    
    /** 
     * @param username Логин
     */
    public void setUsername(String username) {
        this.username = username;
    }

    
    /** 
     * @return String
     */
    @Override
    public String getPassword() {
        return password;
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
    public String getFirstName() {
        return firstName;
    }

    
    /** 
     * @param firstName Имя
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    
    /** 
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

    
    /** 
     * @param lastName Фамилия
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    
    /** 
     * @param password Пароль
     */
    public void setPassword(String password) {
        this.password = password;
    }

    
    /** 
     * @return Date
     */
    public Date getBirthday() {
        return birthday;
    }

    
    /** 
     * @param birthday Дата рождения
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    
    /** 
     * @return boolean
     */
    public boolean getActive() {
        return active;
    }

    
    /** 
     * @param active Активность
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    
    /** 
     * @return Avatar
     */
    public Avatar getAvatar() {
        return avatar;
    }

    
    /** 
     * @param avatar Аватарка
     */
    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    
    /** 
     * @return Set<Role>
     */
    public Set<Role> getRoles() {
        return roles;
    }

    
    /** 
     * @param roles Роли в системе
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    
    /** 
     * @return Wallet
     */
    public Wallet getWallet() {
        return wallet;
    }

    
    /** 
     * @param wallet Кошелек
     */
    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    
    /** 
     * @return List<Bet>
     */
    public List<Bet> getBets() {
        return bets;
    }

    
    /** 
     * @param bets Ставки
     */
    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }
}
