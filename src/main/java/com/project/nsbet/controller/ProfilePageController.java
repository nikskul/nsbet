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

/**
 * Сервлет страницы профиля
 */
@Controller
public class ProfilePageController {

    private final UserService userService;

    @Autowired
    public ProfilePageController(UserService userService) {
        this.userService = userService;
    }

    /** 
     * Отображает страницу профиля
     * @param model Страница профиля
     * @return String
     */
    @GetMapping("/profile")
    public String view(Map<String, Object> model) {

        User currentUser = userService.getCurrentUser();

        if (currentUser != null)
            model.put("user", currentUser);

        return "profile";
    }

    /** 
     * Обновляет фото пользователя
     * @param file Аватарка
     * @return String
     * @throws IOException
     */
    @PostMapping("/profile/avatar")
    public String update(@RequestParam(name = "file", required = true) MultipartFile file) throws IOException {
        // TODO: Change Logic
//        User currentUser = userService.getCurrentUser();
//
//        if (file != null && currentUser != null) {
//            Avatar avatar = new Avatar();
//            avatar.setName(StringUtils.cleanPath(file.getOriginalFilename()));
//            avatar.setContentType(file.getContentType());
//            avatar.setBytes(file.getBytes());
//            avatar.setSize(file.getSize());
//
//            currentUser.setAvatar(avatar);
//        }

//        userService.save(currentUser);

        return "redirect:/profile";
    }
}
