package com.project.nsbet.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Конфигурафия
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    
    /** 
     * Конфигурация контроллеров
     * @param registry Контроллер конфигурации
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    
    /** 
     * Настройка хранилища ресурсов
     * @param registry Контроллер конфигурации
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

}
