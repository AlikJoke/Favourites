package ru.projects.favourites.user;

import ru.projects.favourites.domain.User;

/**
 * Интерфейс для работы с текущим пользователем.
 * 
 * @author Alimurad A. Ramazanov
 * @since 13.08.2017
 * @version 1.0.0
 *
 */
public interface CurrentUser {

	/**
	 * Получение текущего пользователя из контекста приложения.
	 * <p>
	 * 
	 * @see User
	 * @return возвращает имя текущего пользователя; не может быть {@code null}.
	 */
	String getUsername();
}
