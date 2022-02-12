package com.project.nsbet.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Конфигарация Spring Security
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final DataSource dataSource;
	private final EncoderConfiguration encoderConfiguration;

	@Autowired
	public WebSecurityConfig(DataSource dataSource, EncoderConfiguration encoderConfiguration) {
		this.dataSource = dataSource;
		this.encoderConfiguration = encoderConfiguration;
	}

	
	/** 
	 * Основная конфигурация
	 * @param http Конфигурация
	 * @throws Exception Исключение
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/", "/avatars/**", "/sign-up", "/bootstrap/**", "/main/**", "/images/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
				.logout()
				.permitAll();
	}

	
	/** 
	 * @param auth Настройка аунтефикации
	 * @throws Exception Исключение
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(encoderConfiguration.getPasswordEncoder())
		.usersByUsernameQuery("select username, password, active from users where username=?")
		.authoritiesByUsernameQuery("select users.username, roles.name from users " +
									"inner join users_roles on users_user_id = user_id " + 
									"inner join roles on roles_role_id = role_id where username=?");
	}
}
