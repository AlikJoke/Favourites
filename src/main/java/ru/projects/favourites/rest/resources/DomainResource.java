package ru.projects.favourites.rest.resources;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
}
