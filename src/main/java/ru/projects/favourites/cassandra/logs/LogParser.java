package ru.projects.favourites.cassandra.logs;

import java.io.File;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Интерфейс, предоставляющий методы сервиса парсинга логов и преобразования их
 * в объекты, пригодные для обработки и хранения.
 * 
 * @author Alimurad A. Ramazanov
 * @since 22.08.2017
 * @version 1.0.0
 *
 */
public interface LogParser {

	/**
	 * Парсит файл, указанный в качестве аргумента (или список файлов, если это
	 * директория), преобразовывая логи в объекты типа {@link LogItem}.
	 * <p>
	 * 
	 * @see LogItem
	 * 
	 * @param file
	 *            - файл с логами; не может быть {@code null}.
	 * @return список объектов, не может быть {@code null}.
	 * @throws RuntimeException
	 */
	@NotNull
	List<LogItem> parse(@NotNull File file) throws RuntimeException;

	/**
	 * Парсит файл, путь к которому указан в качестве аргумента (или список
	 * файлов, если это путь до директории ), преобразовывая логи в объекты типа
	 * {@link LogItem}.
	 * <p>
	 * 
	 * @see LogItem
	 * @see {@linkplain #parse(File)}
	 * 
	 * @param filePath
	 *            - путь файла с логами; не может быть {@code null}.
	 * @return список объектов, не может быть {@code null}.
	 * @throws RuntimeException
	 */
	@NotNull
	List<LogItem> parse(@NotNull String filePath) throws RuntimeException;
}
