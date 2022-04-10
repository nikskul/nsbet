package com.project.nsbet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * Веб-приложение на основе Java Spring.
 * Приложение осуществляет прием ставок от пользователей.
 * 
 * @author Никита Скулыбердин
 * @version 1.8
 */

@SpringBootApplication
public class NsbetApplication {
	//TODO: Пополнение кошелька
	public static void main(String[] args) {
		SpringApplication.run(NsbetApplication.class, args);
	}
}
