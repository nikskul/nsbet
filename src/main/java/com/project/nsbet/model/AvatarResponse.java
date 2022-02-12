package com.project.nsbet.model;

/**
 * Класс аватара для представления на странице
 */
public class AvatarResponse {
    
    private String id;
    private String name;
    private Long size;
    private String url;
    private String contentType;
    
    
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
     * @return String
     */
    public String getUrl() {
        return url;
    }
    
    /** 
     * @param url Сссылка
     */
    public void setUrl(String url) {
        this.url = url;
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
}
