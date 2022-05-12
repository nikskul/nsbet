package com.project.nsbet.service;

import com.project.nsbet.model.User;
import com.project.nsbet.model.Wallet;
import com.project.nsbet.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletService {

    private final UserService userService;

    private final UserRepository userRepository;

    public WalletService(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public void replenishmentBalance(Double amount) {
        User currentUser = userService.getCurrentUser();
        Wallet userWallet = currentUser.getWallet();
        BigDecimal balance = userWallet.getBalance();

        userWallet.setBalance(balance.add(BigDecimal.valueOf(amount)));

        userRepository.save(currentUser);
    }
}
