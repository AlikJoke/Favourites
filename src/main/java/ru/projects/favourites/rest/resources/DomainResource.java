package ru.projects.favourites.rest.resources;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import ru.projects.favourites.domain.DomainObject;

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

	@JsonIgnore
	public abstract DomainObject convertToDomainObject(boolean isNew, @NotNull @NotEmpty String username);
}
