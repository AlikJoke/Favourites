package ru.projects.favourites.notification;

import ru.projects.favourites.mail.MailService;

/**
 * Сервис отправки почтовых сообщений по какому-либо действию.
 * 
 * @see MailService
 * 
 * @author Alimurad A. Ramazanov
 * @since 17.08.2017
 * @version 1.0.0
 *
 */
public interface NotificationSender {

	/**
	 * Осуществляет отправку сообщения об успешной регистрации в приложении.
	 * <p>
	 * 
	 * @see {@linkplain MailService#send(String, String, String)}.
	 */
	void successRegistrationNotificationSend();

	/**
	 * Осуществляет отправку сообщения при отсутствии логина в системе в течении
	 * недели. Запускается по расписанию.
	 * <p>
	 * 
	 * @see {@linkplain MailService#send(String, String, String)}.
	 */
	void dailyNotificationSend();
}
