package ru.projects.favourites.notification;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.projects.favourites.dao.DomainOperations;
import ru.projects.favourites.domain.EntityType;
import ru.projects.favourites.domain.User;
import ru.projects.favourites.mail.MailService;

@Service
public class NotificationSenderImpl implements NotificationSender {

	@Autowired
	private MailService mailService;

	@Autowired
	private DomainOperations domainOperations;

	@Override
	public void successRegistrationNotificationSend(String email, String username, String password) {
		mailService.send(email, NotificationPatterns.SUCCESS_REG_SUBJECT,
				String.format(NotificationPatterns.SUCCESS_REG_TEXT, username, password));
	}

	@Override
	public void rememberPasswordSend(String email, String username) {
		User user = domainOperations.findById(EntityType.USER, username);
		if (user == null)
			throw new UsernameNotFoundException("User with same username isn't registered in application!");

		mailService.send(email, NotificationPatterns.FORGOTTEN_PASSWORD_SUBJECT,
				String.format(NotificationPatterns.FORGOTTEN_PASSWORD_TEXT, username, user.getPassword()));
	}

	@Override
	@Scheduled(cron = NotificationPatterns.DAILY_CRON_EXPRESSION)
	public void dailyNotificationSend() {
		domainOperations.findUsers(LocalDateTime.now().minusWeeks(1)).forEach(user -> mailService.send(user.getEmail(),
				NotificationPatterns.ABSENT_SUBJECT, String.format(NotificationPatterns.ABSENT_TEXT, user.getUID())));
	}

	static class NotificationPatterns {

		static final String SUCCESS_REG_SUBJECT = "Success registration in application";
		static final String SUCCESS_REG_TEXT = "You're successfully registered in application! /n Your login is '%s', password is '%s'.";
		static final String DAILY_CRON_EXPRESSION = "0 0 12 1/1 * ?";
		static final String ABSENT_SUBJECT = "Visit our application again!";
		static final String ABSENT_TEXT = "We miss you, %s!";
		static final String FORGOTTEN_PASSWORD_SUBJECT = "Forgotten password";
		static final String FORGOTTEN_PASSWORD_TEXT = "Dear %s! \n You password for your account is '%s'. \n Best regards, admin.";
	}

}
