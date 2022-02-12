package com.project.nsbet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Модель аватарки пользователя
 */
@Entity
@Table(name = "Avatars")
public class Avatar {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "avatar_id")
    private String id;

    private String name;
    private Long size;
    
    @Column(name = "content_type")
    private String contentType;

    @Lob
    private byte[] bytes;

    @OneToOne(mappedBy = "avatar")
    private User user;

    public Avatar() { }

    public Avatar(String id, String name, String contentType, Long size, byte[] bytes) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
        this.size = size;
        this.bytes = bytes;
    }

    
    /** 
     * @return String
     */
    public String getId() {
        return id;
    }

    
    /** 
     * @param id Идентификатор
     */
    public void setId(String id) {
        this.id = id;
    }

    
    /** 
     * @return String
     */
    public String getName() {
        return name;
    }

    
    /** 
     * @param name Название файла
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /** 
     * @return String
     */
    public String getContentType() {
        return contentType;
    }

    
    /** 
     * @param contentType Тип файла
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    
    /** 
     * @return Long
     */
    public Long getSize() {
        return size;
    }

    
    /** 
     * @param size Размер файла
     */
    public void setSize(Long size) {
        this.size = size;
    }

    
    /** 
     * @return byte[]
     */
    public byte[] getBytes() {
        return bytes;
    }

    
    /** 
     * @param bytes Байтовое представление файла
     */
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
