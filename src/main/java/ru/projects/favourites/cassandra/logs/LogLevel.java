package ru.projects.favourites.cassandra.logs;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Перечисление возможных уровней протоколирования.
 * 
 * @author Alimurad A. Ramazanov
 * @since 22.08.2017
 * @version 1.0.0
 *
 */
public enum LogLevel {

	TRACE,

	INFO,

	WARN,

	DEBUG,

	FATAL,

	ERROR,
	
	UNDEFINED;

	@NotNull
	public static LogLevel value(@NotNull @NotEmpty String level) {
		return LogLevel.valueOf(level.toUpperCase());
	}
}
