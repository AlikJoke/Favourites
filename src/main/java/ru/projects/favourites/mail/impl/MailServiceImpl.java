package ru.projects.favourites.mail.impl;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ru.projects.favourites.mail.MailService;

@Service
public class MailServiceImpl implements MailService {

	@Value("${mail.from.email}")
	@Email
	private String fromEmail;

	@Override
	public void send(String email, String theme, String text) {
		Session session = Session.getDefaultInstance(MailProperties.getMailProperties(),
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(fromEmail, "password");
					}
				});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject(theme);
			message.setText(text);

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	@Configurable
	static class MailProperties extends Properties {

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

		static MailProperties props;

		static MailProperties getMailProperties() {
			if (props == null) {
				synchronized (MailProperties.class) {
					if (props == null) {
						props = new MailProperties();
					}
				}
			}

			return props;
		}

		private MailProperties() {
			super();
			this.put(MAIL_SMTP_HOST, mailProtocol.concat(".").concat(mailHost));
			this.put(MAIL_SMTP_SF_PORT, sslPort);
			this.put(MAIL_SMTP_SF_CLASS, sslClass);
			this.put(MAIL_SMTP_AUTH, mailAuth);
			this.put(MAIL_SMTP_PORT, mailPort);
		}
	}

}
