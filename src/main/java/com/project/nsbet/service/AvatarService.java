package com.project.nsbet.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.project.nsbet.exception.InvalidFileTypeException;
import com.project.nsbet.model.Avatar;
import com.project.nsbet.repository.AvatarRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;

    private final String[] ALLOWED_CONTENT_TYPES = {"image/png", "image/jpeg"};

    @Autowired
    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    public Avatar save(MultipartFile file) throws IOException {
        String fileContentType = file.getContentType();
        if (Arrays.asList(ALLOWED_CONTENT_TYPES).contains(fileContentType)) {
            Avatar avatar = new Avatar();
            avatar.setName(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
            avatar.setContentType(fileContentType);
            try {
                avatar.setBytes(file.getBytes());
            } catch (IOException e) {
                throw new IOException("Не удалось обработать файл.");
            }
            avatar.setSize(file.getSize());
            return avatarRepository.save(avatar);
        } else {
            throw new InvalidFileTypeException("Формат файла не поддерживается. Только картинки: jpeg, png.");
        }
    }
    public Optional<Avatar> getAvatar(String id) {
        return avatarRepository.findById(id);
    }

    public List<Avatar> getAllAvatars() {
        return avatarRepository.findAll();
    }
}
