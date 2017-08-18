package ru.projects.favourites.mail.impl;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Класс, описывающий свойства, необходимые для отправки сообщения через
 * smtp-сервер.
 * 
 * @author Alimurad A. Ramazanov
 * @since 16.08.2017
 * @version 1.0.0
 *
 */
@Component
public class MailProperties extends Properties {

	private static final long serialVersionUID = -146992741616212941L;

	static final String MAIL_SMTP_HOST = "mail.smtp.host";
	static final String MAIL_SMTP_SF_PORT = "mail.smtp.socketFactory.port";
	static final String MAIL_SMTP_SF_CLASS = "mail.smtp.socketFactory.class";
	static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
	static final String MAIL_SMTP_PORT = "mail.smtp.port";

	@Value("${spring.mail.host}")
	private String mailHost;

	@Value("${spring.mail.protocol}")
	private String mailProtocol;

	@Value("${spring.mail.smtp.socketFactory.class}")
	private String sslClass;

	@Value("${spring.mail.port}")
	private int mailPort;

	@Value("${spring.mail.smtp.socketFactory.port}")
	private int sslPort;

	@Value("${spring.mail.smtp.auth}")
	private boolean mailAuth;
	
	@PostConstruct
	public void init() {
		this.put(MAIL_SMTP_HOST, mailProtocol.concat(".").concat(mailHost));
		this.put(MAIL_SMTP_SF_PORT, sslPort);
		this.put(MAIL_SMTP_SF_CLASS, sslClass);
		this.put(MAIL_SMTP_AUTH, mailAuth);
		this.put(MAIL_SMTP_PORT, mailPort);
	}
}
