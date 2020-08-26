package com.santosediego.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.santosediego.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);//SimpleMailMessage add no pom.xml;
}