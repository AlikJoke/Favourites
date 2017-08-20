package ru.projects.favourites.notification;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

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
	 * @param email
	 *            - адрес почты, на который будет отправлено уведомление; не
	 *            может быть {@code null}.
	 * @param username
	 *            - имя пользователя (логин); не может быть {@code null}.
	 * @param password
	 *            - пароль пользователя; не может быть {@code null}.
	 * 
	 * @see {@linkplain MailService#send(String, String, String)}.
	 */
	void successRegistrationNotificationSend(@NotNull @Email String email, @NotNull String username,
			@NotNull String password);

	/**
	 * Осуществляет отправку сообщения, содержащего данные о параметрах доступа
	 * к приложению для пользователя.
	 * <p>
	 * 
	 * @param email
	 *            - адрес почты, на который будет отправлено уведомление; не
	 *            может быть {@code null}.
	 * @param username
	 *            - имя пользователя (логин); не может быть {@code null}.
	 * 
	 * @see {@linkplain MailService#send(String, String, String)}.
	 */
	void rememberPasswordSend(@NotNull @Email String email, @NotNull String username);

	/**
	 * Осуществляет отправку сообщения при отсутствии логина в системе в течении
	 * недели. Запускается по расписанию.
	 * <p>
	 * 
	 * @see {@linkplain MailService#send(String, String, String)}.
	 */
	void dailyNotificationSend();
}
