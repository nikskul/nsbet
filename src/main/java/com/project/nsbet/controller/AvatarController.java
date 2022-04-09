package com.project.nsbet.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.project.nsbet.model.Avatar;
import com.project.nsbet.model.AvatarResponse;
import com.project.nsbet.service.AvatarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/avatars")
public class AvatarController {

    private final AvatarService avatarService;

    @Autowired
    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    /**
     * Сохраняет файл в БД
     * @param file Файл
     * @return ResponseEntity<String>
     */
    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            avatarService.save(file);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Файл успешно загружен: %s", file.getOriginalFilename()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Не удалось загрузить файл: %s!", file.getOriginalFilename()));
        }
    }

    /** 
     * Возвращает список файлов из БД
     * @return List<AvatarResponse>
     */
    @GetMapping
    public List<AvatarResponse> list() {
        return avatarService.getAllAvatars()
                .stream()
                .map(this::mapToAvatarResponse)
                .collect(Collectors.toList());
    }

    /** 
     * Обработка файла в представление
     * @param avatar Аватарка
     * @return AvatarResponse
     */
    private AvatarResponse mapToAvatarResponse(Avatar avatar) {
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(avatar.getId())
                .toUriString();
        AvatarResponse avatarResponse = new AvatarResponse();
        avatarResponse.setId(avatar.getId());
        avatarResponse.setName(avatar.getName());
        avatarResponse.setContentType(avatar.getContentType());
        avatarResponse.setSize(avatar.getSize());
        avatarResponse.setUrl(downloadURL);

        return avatarResponse;
    }

    /** 
     * Возвращает файл
     * @param id Идентификатор файла
     * @return ResponseEntity<byte[]>
     */
    @GetMapping("{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Optional<Avatar> avatarOptional = avatarService.getAvatar(id);

        if (avatarOptional.isEmpty()) {
            return ResponseEntity.notFound()
                    .build();
        }

        Avatar avatar = avatarOptional.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + avatar.getName() + "\"")
                .contentType(MediaType.valueOf(avatar.getContentType()))
                .body(avatar.getBytes());
    }
}
