package com.santosediego.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.santosediego.cursomc.services.DBService;
import com.santosediego.cursomc.services.EmailService;
import com.santosediego.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService;

	@Bean
	public boolean instantiateDatabase() throws ParseException {

		dbService.instantiateTestDatabase();
		return true;
	}
	
	@Bean//Fica disponivoel como um componete para o sistema;
	public EmailService emailService() {
		return new MockEmailService();
	}
}