package com.project.nsbet.service;

import com.project.nsbet.configuration.EncoderConfiguration;
import com.project.nsbet.exception.AlreadyExistException;
import com.project.nsbet.exception.CredentialVerificationException;
import com.project.nsbet.model.User;
import com.project.nsbet.model.Wallet;
import com.project.nsbet.repository.RoleRepository;
import com.project.nsbet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collections;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EncoderConfiguration encoderConfiguration;
    private final RoleRepository roleRepository;

    private final AvatarService avatarService;

    @Autowired
    public UserService(UserRepository userRepository,
                       EncoderConfiguration encoderConfiguration,
                       RoleRepository roleRepository,
                       AvatarService avatarService
    ) {
        this.userRepository = userRepository;
        this.encoderConfiguration = encoderConfiguration;
        this.roleRepository = roleRepository;
        this.avatarService = avatarService;
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username).orElse(null);
    }

    private void validateUser(User newUser, String passwordVerification)
            throws CredentialVerificationException, AlreadyExistException {

        if (!newUser.getPassword().equals(passwordVerification))
            throw new CredentialVerificationException("Пароль и подтверждение пароля не совпадают.");
        if (userRepository.findByUsername(newUser.getUsername()).isPresent())
            throw new AlreadyExistException("Логин " + newUser.getUsername() + " уже занят. Измените логин.");
    }

    private void initUser(User user) {
        Wallet wallet = new Wallet();
        user.setWallet(wallet);
        wallet.setUser(user);

        user.setActive(true);
        user.setRoles(Collections.singleton(roleRepository.findByName("ROLE_USER")));
        user.setPassword(encoderConfiguration.getPasswordEncoder().encode(user.getPassword()));
    }

    private void setAvatarToUser(User user, MultipartFile file)
            throws IOException {

        user.setAvatar(avatarService.save(file));
    }

    @Transactional
    public void registerUser(User user, String passwordVerification, MultipartFile file)
            throws CredentialVerificationException, AlreadyExistException, IOException {

        validateUser(user, passwordVerification);
        initUser(user);

        if (!file.isEmpty())
            setAvatarToUser(user, file);

        userRepository.saveAndFlush(user);
    }

    @Transactional
    public void updateUserAvatar(User userToUpdate, MultipartFile file)
            throws IOException {

        userToUpdate.setAvatar(avatarService.save(file));
        userRepository.save(userToUpdate);
    }
}
