package com.santosediego.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.santosediego.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
		// !Template Message = consegue implementar o metodo baseado em metodos
		// abstratos que dpois vão ser implementados pelas implementações da inferface;
	}

	// protected pois pode ser acessado por subclasses, só não pode ser usado pelos
	// usuários das classes(controladores e serviços;
	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {

		SimpleMailMessage sm = new SimpleMailMessage();

		sm.setTo(obj.getCliente().getEmail());// Email do cliente;
		sm.setFrom(sender);// Email padrão definido no application.properties;
		sm.setSubject("Pedido confirmado! Código: " + obj.getId());// Assunto do email;
		sm.setSentDate(new Date(System.currentTimeMillis()));// Garantir que vai a data do servidor;
		sm.setText(obj.toString());// Assunto do email, definimos o toString do pedido;

		return sm;
	}
}