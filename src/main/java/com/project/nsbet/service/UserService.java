package com.project.nsbet.service;

import com.project.nsbet.model.User;
import com.project.nsbet.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с {@link User}
 */
@Service
public class UserService {
    
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    
    /** 
     * Возвращает текущего пользователя из БД
     * @return User
     */
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();     
        String username = auth.getName();
        return userRepository.findByUsername(username);
    }

    
    /** 
     * Сохраняет пользователя в БД
     * @param user пользователь для сохранения
     */
    public void save(User user) {
        userRepository.save(user);
    }
}
