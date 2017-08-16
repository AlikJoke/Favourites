package ru.projects.favourites.rest.controllers;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import ru.projects.favourites.rest.resources.DomainResource;

/**
 * Интерфейс, предоставляющий методы для работы с ресурсами.
 * 
 * @author Alimurad A. Ramazanov
 * @since 15.08.2017
 * @version 1.0.0
 *
 */
public interface RESTOperations {

	/**
	 * Базовый URI. Используется для POST на создание ресурсов и GET за списком
	 * всех ресурсов указанного типа.
	 */
	static final String BASE_URI = "/favourites/{entityType}";

	/**
	 * URI ресурса.
	 */
	static final String RESOURCE_URI = "/favourites/{entityType}/{uid}";

	/**
	 * Метод получения ресурса указанного типа по идентификатору.
	 * <p>
	 * 
	 * @see DomainResource
	 * @see FavouriteResource
	 * @see UserResource
	 * 
	 * @param id
	 *            - уникальный идентификатор объекта; не может быть
	 *            {@code null}.
	 * @param entityType
	 *            - тип сущности; не может быть {@code null}.
	 * @return может быть {@code null}, если объект с указанным идентификатором
	 *         не найден.
	 */
	@Null
	DomainResource readById(@NotNull String id, @NotNull String entityType);

	/**
	 * Метод для получения всех объектов типа "Избранное". <b>Только для
	 * избранных. Не для списка пользователей.</b>
	 * <p>
	 * 
	 * @see DomainResource
	 * 
	 * @param entityType
	 *            - тип сущности; не может быть {@code null}.
	 * @param username
	 *            - имя текущего пользователя, для которого получается список
	 *            всех избранных; не может быть {@code null}.
	 * @return не может быть {@code null}.
	 */
	@NotNull
	List<DomainResource> readAll(@NotNull String entityType, @NotNull String username);

	/**
	 * Метод для создания ресурса указанного типа. <p.
	 * 
	 * @see DomainResource
	 * 
	 * @param entityType
	 *            - тип сущности; не может быть {@code null}.
	 * @param resource
	 *            - ресурс для создания; не может быть {@code null}.
	 * @param username
	 *            - имя текущего пользователя, для которого создается объект; не
	 *            может быть {@code null}.
	 */
	void create(@NotNull String entityType, @NotNull DomainResource resource, @NotNull String username);

	/**
	 * Метод для обновления данных об объекте в системе.
	 * <p>
	 * 
	 * @see DomainResource
	 * 
	 * @param entityType
	 *            - тип сущности; не может быть {@code null}.
	 * @param resource
	 *            - ресурс для обновления; не может быть {@code null}.
	 * @return не может быть {@code null}.
	 */
	@NotNull
	DomainResource update(@NotNull String entityType, @NotNull DomainResource resource);

	/**
	 * Метод для удаления объекта указанного типа по уникальному идентификатору.
	 * <p>
	 * 
	 * @param entityType
	 *            - тип сущности; не может быть {@code null}.
	 * @param uid
	 *            - уникальный идентификатор объекта; не может быть
	 *            {@code null}.
	 */
	void delete(@NotNull String entityType, @NotNull String uid);
}
