package com.project.nsbet.service;

import com.project.nsbet.model.Wallet;

import org.springframework.stereotype.Service;

/**
 * Сервис для работы с {@link Wallet}
 */
@Service
public class WalletService {

    /** 
     * Создает и возвращает новый объект {@link Wallet} 
     * @return Wallet
     */
    public Wallet createWallet() {
        return new Wallet();
    }
    
}
