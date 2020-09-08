package com.santosediego.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.santosediego.cursomc.domain.Cliente;
import com.santosediego.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Autowired
	private TemplateEngine templateEngine;// Processar o objeto no template;

	@Autowired
	private JavaMailSender javaMailSender;// Para instanciar um MimeMessage;

	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);//função abaixo;
		sendEmail(sm);// EmailService
		// !Template Message = consegue implementar o metodo baseado em metodos
		// abstratos que dpois vão ser implementados pelas implementações da inferface;
	}

	// protected pois pode ser acessado por subclasses, só não pode ser usado pelos
	// usuários das classes(controladores e serviços);
	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {

		SimpleMailMessage sm = new SimpleMailMessage();

		sm.setTo(obj.getCliente().getEmail());// Email do cliente;
		sm.setFrom(sender);// Email padrão definido no application.properties;
		sm.setSubject("Pedido confirmado! Código: " + obj.getId());// Assunto do email;
		sm.setSentDate(new Date(System.currentTimeMillis()));// Garantir que vai a data do servidor;
		sm.setText(obj.toString());// Assunto do email, definimos o toString do pedido;

		return sm;
	}

	// Método que será responsável por retornar o HTML preenchido com os dados do
	// pedido, a partir do Thumeleaf
	protected String htmlFromTemplatePedido(Pedido obj) {

		// Este objeto é necessáio para acessar o template;
		Context context = new Context();

		// definimos o apelido de pedido pois é o que esta no html;
		context.setVariable("pedido", obj);

		// Ira processar o template, passar o caminho do arquivo html e o nome. Não é
		// necessáro passar oresouces/template pois é padrão do framework;
		return templateEngine.process("email/confirmacaoPedido", context);
	}

	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		

		// Tratando a excessão do método prepareMimeMessageFromPedido, caso apresentar
		// exceção ao enviar o emil em html ele enviará o email em texto plano;
		try {
			MimeMessage mm = prepareMimeMessageFromPedido(obj);// Função abaixo;
			sendHtmlEmail(mm);
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(obj);
		}
	}

	// Protected para abrir a possibilidade de ser utilizar por outras subclasse;
	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		// Para pegar o pedido obj e gerar um objeto do tipo MimeMessage, injeta um
		// JavaMailSender;
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		// Para dar o poder de atribuir valores a essa mensagem, instanciar o
		// MimeMessageHelper;
		// apresenta exceção mas vamos tratar no metodo
		// sendOrderConfirmationHtmlEmail();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);// O booleah;

		// Atribuir os valores para o email;
		mmh.setTo(obj.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Pedido confirmado! Código: " + obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(obj), true);// true confirma que é arquivo html;

		return mimeMessage;
	}

	@Override
	public void sendNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(cliente, newPass);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(cliente.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha " + newPass);
		return sm;
	}
}