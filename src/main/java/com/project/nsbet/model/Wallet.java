package com.project.nsbet.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Модель кошелька
 */
@Entity
@Table(name = "Wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wallet_id")
    private Long id;
    private BigDecimal balance;

    @OneToOne(mappedBy = "wallet")
    private User user;

    public Wallet() { 
        balance = new BigDecimal(0d);
    }

    
    /** 
     * Геттер id
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
     * @return BigDecimal
     */
    public BigDecimal getBalance() {
        return balance;
    }

    
    /** 
     * @param balance Баланс
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    
    /** 
     * @return User
     */
    public User getUser() {
        return user;
    }

    
    /** 
     * @param user Владелец
     */
    public void setUser(User user) {
        this.user = user;
    }
}
