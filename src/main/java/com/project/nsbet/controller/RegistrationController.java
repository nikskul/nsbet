package com.project.nsbet.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.project.nsbet.exception.AlreadyExistException;
import com.project.nsbet.exception.CredentialVerificationException;
import com.project.nsbet.model.User;
import com.project.nsbet.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Сервлет регистрации
 */
@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param model Представление страницы
     * @return String
     */
    @GetMapping("/sign-up")
    public String getPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    /**
     * @param newUser              Объект {@link User} с формы регистрации
     * @param passwordVerification Подтверждение пароля
     * @param file                 Файл пользователя из формы
     * @param model                Представление страницы
     * @return String              Страница
     */
    @PostMapping("/sign-up")
    public String addUser(
            User newUser,
            String passwordVerification,
            @RequestParam(name = "file", required = false) MultipartFile file,
            Map<String, Object> model
    ) {
        try {
            userService.saveUser(newUser, passwordVerification, file);
        } catch (AlreadyExistException e) {
            model.put("userAlreadyExistException", e.getMessage());
            return "registration";
        } catch (CredentialVerificationException e) {
            model.put("badCredentials", e.getMessage());
            return "registration";
        } catch (IOException e) {
            model.put("fileError", e.getMessage());
            return "registration";
        }
        return "redirect:/login";
    }
}
