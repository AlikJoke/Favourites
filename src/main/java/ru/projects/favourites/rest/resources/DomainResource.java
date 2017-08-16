package ru.projects.favourites.rest.resources;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import ru.projects.favourites.domain.DomainObject;
import ru.projects.favourites.domain.Favourite;
import ru.projects.favourites.domain.User;

/**
 * JSON-ресурс доменного объекта {@link DomainObject}.
 * 
 * @author Alimurad A. Ramazanov
 * @since 15.08.2017
 * @version 1.0.0
 *
 */
public abstract class DomainResource implements Serializable {

	private static final long serialVersionUID = -2082770212923584290L;

	private final String uid;
	private final LocalDateTime deletingDT;
	private final boolean isDeleted;

	@JsonCreator
	public DomainResource(@JsonProperty("uid") String uid, @JsonProperty("deletingDT") LocalDateTime deletingDT,
			@JsonProperty("isDeleted") boolean isDeleted) {
		this.uid = uid;
		this.deletingDT = deletingDT;
		this.isDeleted = isDeleted;
	}

	public DomainResource(DomainObject domain) {
		this.uid = domain.getUID();
		this.deletingDT = domain.getDeletingDT();
		this.isDeleted = domain.isDeleted();
	}

	@JsonProperty("uid")
	public String getUID() {
		return this.uid;
	}

	@JsonProperty("deletingDT")
	public LocalDateTime getDeletingDT() {
		return this.deletingDT;
	}

	@JsonProperty("isDeleted")
	public boolean isDeleted() {
		return this.isDeleted;
	}

	@NotNull
	@JsonIgnore
	public static final DomainResource convertToResource(@NotNull DomainObject domain) {
		if (domain instanceof Favourite)
			return new FavouriteResource((Favourite) domain);
		else if (domain instanceof User)
			return new UserResource((User) domain);
		else
			throw new IllegalArgumentException();
	}

	/**
	 * Выполняет преобразование JSON-ресурса в доменный объект, один из
	 * наследников {@link DomainObject}.
	 * <p>
	 * 
	 * @see DomainObject
	 * 
	 * @param isNew
	 *            - признак, что объект новый.
	 * @param username
	 *            - имя пользователя, создающего ресурс.
	 * 
	 * @return не может быть {@code null}.
	 */
	@JsonIgnore
	@NotNull
	public abstract DomainObject convertToDomainObject(boolean isNew, @NotNull @NotEmpty String username);
}
