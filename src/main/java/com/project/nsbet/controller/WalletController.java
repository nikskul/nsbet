package com.project.nsbet.controller;

import com.project.nsbet.model.User;
import com.project.nsbet.service.UserService;
import com.project.nsbet.service.WalletService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class WalletController {

    private final UserService userService;
    private final WalletService walletService;

    public WalletController(UserService userService, WalletService walletService) {
        this.userService = userService;
        this.walletService = walletService;
    }

    @GetMapping("/wallet")
    public String getPage(Map<String, Object> model) {

        User currentUser = userService.getCurrentUser();

        model.put("user", currentUser);

        return "wallet";
    }


    @PostMapping("/wallet")
    public String replenishmentBalance(@RequestParam(name = "replenishmentAmount") Double amount,
                                       Map<String, Object> model) {
        walletService.replenishmentBalance(amount);
        User currentUser = userService.getCurrentUser();

        model.put("user", currentUser);

        return "wallet";
    }
}
