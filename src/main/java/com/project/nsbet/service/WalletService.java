package com.project.nsbet.service;

import com.project.nsbet.model.Wallet;

import org.springframework.stereotype.Service;

/**
 * Сервис для работы с {@link Wallet}
 */
@Service
public class WalletService {

    public Wallet createWallet() {
        return new Wallet();
    }
    
}
