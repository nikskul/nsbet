package com.project.nsbet.controller;

import java.io.IOException;
import java.util.Map;

import com.project.nsbet.model.User;
import com.project.nsbet.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProfilePageController {
    // TODO: Add Profile Delete

    private final UserService userService;

    @Autowired
    public ProfilePageController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Отображает страницу профиля
     *
     * @param model Страница профиля
     * @return String
     */
    @GetMapping("/profile")
    public String getPage(Map<String, Object> model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null)
            model.put("user", currentUser);
        return "profile";
    }

    /**
     * Обновляет фото пользователя
     *
     * @param file Аватарка
     * @return String
     * @throws IOException
     */
    @PostMapping("/profile/avatar")
    public String update(Map<String, Object> model,
                         @RequestParam(name = "file", required = true) MultipartFile file
    ) {
        User currentUser = userService.getCurrentUser();
        try {
            userService.updateUserAvatar(currentUser, file);
        } catch (IOException e) {
            model.put("fileError", e.getMessage());
            return "profile";
        }
        return "redirect:/profile";
    }

}
