package ru.projects.favourites.mail;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Интерфейс работы с сервисом отправки сообщений на почту.
 * 
 * @see {@link MailServiceImpl}.
 * 
 * @author Alimurad A. Ramazanov
 * @since 16.08.2017
 * @version 1.0.0
 *
 */
public interface MailService {

	/**
	 * Метод выполняет отправку сообщения с указанной темой и содержанием на
	 * указанный адрес эл. почты.
	 * <p>
	 * 
	 * @param email
	 *            - адрес почты адресата; не может быть {@code null}.
	 * @param theme
	 *            - тема сообщения; не может быть {@code null}.
	 * @param text
	 *            - текст сообщения; может быть {@code null}.
	 */
	void send(@NotNull @NotEmpty @Email String email, @NotNull @NotEmpty String theme, @Null String text);
}
