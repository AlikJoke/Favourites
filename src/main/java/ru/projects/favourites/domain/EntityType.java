package ru.projects.favourites.domain;

import javax.validation.constraints.NotNull;

/**
 * Перечисление типов объектов, с которыми работает приложение.
 * 
 * @author Alimurad A. Ramazanov
 * @since 16.08.2017
 * @version 1.0.0
 *
 */
public enum EntityType {

	/**
	 * Элемент избранного.
	 */
	FAVOURITE("favourite"),

	/**
	 * Объект пользователя.
	 */
	USER("user_t");

	protected final String name;

	EntityType(@NotNull String name) {
		this.name = name;
	}

	@NotNull
	public static EntityType value(@NotNull String name) {
		if (name.equalsIgnoreCase(FAVOURITE.getName()))
			return FAVOURITE;
		else if (name.equalsIgnoreCase(USER.getName()))
			return USER;
		else
			return EntityType.valueOf(name.toUpperCase());
	}

	@NotNull
	public String getName() {
		return name;
	}
}
