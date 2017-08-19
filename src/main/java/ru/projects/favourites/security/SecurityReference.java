package ru.projects.favourites.security;

import javax.validation.constraints.NotNull;

import ru.projects.favourites.rest.resources.UserResource;

/**
 * Интерфейс работы с аутентификацией пользователей в приложении.
 * 
 * @author Alimurad A. Ramazanov
 * @since 19.08.2017
 * @version 1.0.0
 *
 */
public interface SecurityReference {

	/**
	 * Авторизация в приложении по имени и паролю.
	 * <p>
	 * 
	 * @param username
	 *            - имя пользователя; не может быть {@code null}.
	 * @param password
	 *            - пароль пользователя; не может быть {@code null}.
	 * @throws UserNotFoundException
	 */
	void login(@NotNull String username, @NotNull String password);

	/**
	 * Завершение сеанса работы с приложением для пользователя с указанным
	 * именем.
	 */
	void logout();

	/**
	 * Регистрация нового пользователя в приложении.
	 * <p>
	 * 
	 * @see UserResource
	 * 
	 * @param user
	 *            - ресурс создаваемого пользователя; не может быть
	 *            {@code null}.
	 */
	void create(@NotNull UserResource user);
}
