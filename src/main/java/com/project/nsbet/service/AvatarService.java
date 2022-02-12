package com.project.nsbet.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.project.nsbet.model.Avatar;
import com.project.nsbet.repository.AvatarRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * Сервис для работы с {@link Match}
 */
@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;

    @Autowired
    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    
    /** 
     * Сохраняет файл в БД
     * @param file Файл для сохранения
     * @throws IOException
     */
    public void save(MultipartFile file) throws IOException {
        Avatar avatar = new Avatar();
        avatar.setName(StringUtils.cleanPath(file.getOriginalFilename()));
        avatar.setContentType(file.getContentType());
        avatar.setBytes(file.getBytes());
        avatar.setSize(file.getSize());
        
        avatarRepository.save(avatar);
    }

    
    /** 
     * Возвращает файл по идентификатору
     * @param id Идентификатор
     * @return Optional<Avatar>
     */
    public Optional<Avatar> getAvatar(String id) {
        return avatarRepository.findById(id);
    }

    
    /** 
     * Возвращает List всех {@link Avatar} из БД
     * @return List<Avatar>
     */
    public List<Avatar> getAllAvatars() {
        return avatarRepository.findAll();
    }
}
