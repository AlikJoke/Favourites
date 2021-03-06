package ru.projects.favourites.domain;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Абстрактный класс, содержащий общую информацию для доменных объектов системы.
 * Наследники: {@link User}, {@link Favourite}.
 * 
 * @author Alimurad A. Ramazanov
 * @since 15.08.2017
 * @version 1.0.0
 *
 */
public abstract class DomainObject {

	private boolean isNew;
	private boolean isDeleted;

	@Null
	private LocalDateTime deletingDT;

	@NotNull
	protected String uid;

	protected DomainObject(String uid, LocalDateTime deletingDT) {
		this.uid = uid;
		this.isNew = false;
		this.isDeleted = deletingDT != null;
		this.deletingDT = deletingDT;
	}

	/**
	 * Только для новых сущностей. <b> Для сохранения в СУБД. </b>
	 * <p>
	 * 
	 * @param uid
	 *            - идентификатор сущности; не может быть {@code null}.
	 */
	protected DomainObject(@NotNull @NotEmpty String uid) {
		this.uid = uid;
		this.isNew = true;
		this.isDeleted = false;
		this.deletingDT = null;
	}

	/**
	 * @see {@link #DomainObject(String)}
	 * @param uid
	 */
	protected DomainObject(int uid) {
		this(Integer.toString(uid));
	}

	/**
	 * Возвращает уникальный идентификатор объекта.
	 * <p>
	 * 
	 * @return не может быть {@code null}.
	 */
	@NotNull
	@NotEmpty
	public abstract String getUID();

	/**
	 * Возвращает тип объекта.
	 * <p>
	 * 
	 * @return не может быть {@code null}.
	 */
	@NotNull
	public abstract String getEntityName();

	public boolean isNew() {
		return this.isNew;
	}

	public boolean isDeleted() {
		return this.isDeleted;
	}

	public LocalDateTime getDeletingDT() {
		return this.deletingDT;
	}
}
