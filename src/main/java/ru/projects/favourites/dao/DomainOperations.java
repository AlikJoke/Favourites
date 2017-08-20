package ru.projects.favourites.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotEmpty;

import ru.projects.favourites.dao.impl.DomainOperationsImpl;
import ru.projects.favourites.domain.DomainObject;
import ru.projects.favourites.domain.EntityType;
import ru.projects.favourites.domain.User;

/**
 * Интерфейс доступа к операциям с БД.
 * 
 * @see DomainObject
 * @see DomainOperationsImpl
 * 
 * @author Alimurad A. Ramazanov
 * @since 14.08.2017
 * @version 1.0.0
 * 
 */
public interface DomainOperations {

	/**
	 * Сохранение нового объекта в приложении.
	 * <p>
	 * 
	 * @see DomainObject
	 * 
	 * @param domainObject
	 *            - объект для сохранения; не может быть {@code null}.
	 */
	void save(@NotNull DomainObject domainObject);

	/**
	 * Обновление существующего объекта в приложении.
	 * <p>
	 * 
	 * @see DomainObject
	 * @param current
	 *            - новая версия объекта для обновления; не может быть
	 *            {@code null}.
	 * @param entityType
	 *            - тип объекта для сохранения; не может быть {@code null}.
	 */
	void update(@NotNull EntityType entityType, @NotNull DomainObject current);

	/**
	 * Удаление объекта из приложения.
	 * <p>
	 * 
	 * @param entity
	 *            - объект для удаления; не может быть {@code null}.
	 * @return {@code true} - если удаление прошло успешно, {@code false} -
	 *         иначе.
	 */
	boolean remove(@NotNull DomainObject entity);

	/**
	 * Удаление объекта по ключу и его типу.
	 * <p>
	 * 
	 * @see DomainObject
	 * 
	 * @param entityType
	 *            - тип объекта для удаления; не может быть {@code null}.
	 * @param key
	 *            - идентификатор объекта; не может быть {@code null}.
	 */
	boolean remove(@NotNull EntityType entityType, @NotNull String key);

	/**
	 * Метод для получения объекта по его типу и идентификатору.
	 * <p>
	 * 
	 * @see EntityType
	 * @see DomainObject
	 * 
	 * @param entityType
	 *            - тип объекта для удаления; не может быть {@code null}.
	 * @param uid
	 *            - идентификатор объекта; не может быть {@code null}.
	 * @return может быть {@code null}, если объекта с таким идентификатором
	 *         нет.
	 */
	@Null
	<T extends DomainObject> T findById(@NotNull EntityType entityType, @NotNull String uid);

	/**
	 * Метод для получения списка всех объектов избранных.
	 * <p>
	 * 
	 * @param username
	 *            - имя пользователя, для которого происходит поиск; не может
	 *            быть {@code null}.
	 * @return не может быть {@code null}.
	 */
	@NotNull
	List<? extends DomainObject> findAll(@NotNull @NotEmpty String username);

	/**
	 * Метод для получения списка всех объектов избранных, удовлетворяющих
	 * строке фильтра.
	 * <p>
	 * 
	 * @param username
	 *            - имя пользователя, для которого происходит поиск; не может
	 *            быть {@code null}.
	 * @param filteringValue
	 *            - строка фильтра; не может быть {@code null}.
	 * @return не может быть {@code null}.
	 */
	@NotNull
	List<? extends DomainObject> findWithFilter(@NotNull @NotEmpty String username, @NotNull String filteringValue);

	/**
	 * Метод для получения списка пользователей, у которых время последнего
	 * логина было до указанной даты.
	 * <p>
	 * 
	 * @param lastLoggedDT
	 *            - время последнего логина; не может быть {@code null}.
	 * @return не может быть {@code null}.
	 */
	@NotNull
	List<User> findUsers(@NotNull @Past LocalDateTime lastLogged);
}
