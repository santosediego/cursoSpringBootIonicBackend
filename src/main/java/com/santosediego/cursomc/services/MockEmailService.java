package com.santosediego.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

//Simular o envio do email no logger do programa;
public class MockEmailService extends AbstractEmailService{

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("\nSimulando envio de email...");
		LOG.info("\n" + msg.toString());
		LOG.info("\nEmail enviado.");
	}
}