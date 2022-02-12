package com.project.nsbet.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import com.project.nsbet.configuration.EncoderConfiguration;
import com.project.nsbet.model.Avatar;
import com.project.nsbet.model.Role;
import com.project.nsbet.model.User;
import com.project.nsbet.model.Wallet;
import com.project.nsbet.repository.UserRepository;
import com.project.nsbet.service.WalletService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Сервлет регистрации
 */
@Controller
public class RegistrationController {

    private final UserRepository userRepository;
    private final EncoderConfiguration encoderConfiguration;
    private final WalletService walletService;

    @Autowired
    public RegistrationController(
            UserRepository userRepository,
            EncoderConfiguration encoderConfiguration,
            WalletService walletService) {
        this.userRepository = userRepository;
        this.encoderConfiguration = encoderConfiguration;
        this.walletService = walletService;
    }

    /**
     * @param model Представление страницы
     * @return String
     */
    @GetMapping("/sign-up")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    /**
     * @param newUser              Объект {@link User} с формы регистрации
     * @param passwordVerification Подтверждение пароля
     * @param inputDate            Дата рождения из формы
     * @param file                 Фото пользователя из формы
     * @param model                Представление страницы
     * @return String
     * @throws ParseException
     * @throws IOException
     */
    @PostMapping("/sign-up")
    public String addUser(User newUser, String passwordVerification, String inputDate,
            @RequestParam(name = "file", required = false) MultipartFile file, Map<String, Object> model)
            throws ParseException, IOException {

        User userFromDb = userRepository.findByUsername(newUser.getUsername());

        if (userFromDb != null) {
            model.put("existError", "Пользователь с таким логином уже зарегистрирован!");
            return "registration";
        }

        if (!newUser.getPassword().equals(passwordVerification)) {
            model.put("isVerificationError", true);
            return "registration";
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed = format.parse(inputDate);
        newUser.setBirthday(parsed);

        newUser.setActive(true);
        newUser.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        newUser.setPassword(encoderConfiguration.getPasswordEncoder().encode(newUser.getPassword()));

        Wallet wallet = walletService.createWallet();
        newUser.setWallet(wallet);
        wallet.setUser(newUser);

        if (file != null) {
            Avatar avatar = new Avatar();
            avatar.setName(StringUtils.cleanPath(file.getOriginalFilename()));
            avatar.setContentType(file.getContentType());
            avatar.setBytes(file.getBytes());
            avatar.setSize(file.getSize());

            newUser.setAvatar(avatar);
        }

        userRepository.save(newUser);

        return "redirect:/login";
    }
}
