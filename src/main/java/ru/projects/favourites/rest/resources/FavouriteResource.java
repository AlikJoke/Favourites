package ru.projects.favourites.rest.resources;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FavouriteResource extends DomainResource {

	private static final long serialVersionUID = 7969317377676043228L;

	@NotNull
	@NotEmpty
	private final String name;

	@NotNull
	@NotEmpty
	private final String link;

	@NotNull
	private final LocalDateTime addingDT;

	@NotNull
	private final Integer order;

	@NotNull
	private final Long counter;

	@JsonCreator
	public FavouriteResource(@JsonProperty("uid") String uid, @JsonProperty("deletingDT") LocalDateTime deletingDT,
			@JsonProperty("isDeleted") boolean isDeleted, @JsonProperty("name") String name,
			@JsonProperty("link") String link, @JsonProperty("addingDT") LocalDateTime addingDT,
			@JsonProperty("order") Integer order, @JsonProperty("counter") Long counter) {
		super(uid, deletingDT, isDeleted);
		this.name = name;
		this.link = link;
		this.addingDT = addingDT;
		this.order = order;
		this.counter = counter;
	}

	@JsonProperty("name")
	public String getName() {
		return this.name;
	}

	@JsonProperty("addingDT")
	public LocalDateTime getAddingDT() {
		return this.addingDT;
	}

	@JsonProperty("link")
	public String getLink() {
		return this.link;
	}

	@JsonProperty("order")
	public Integer getOrder() {
		return this.order;
	}

	@JsonProperty("counter")
	public Long getCounter() {
		return this.counter;
	}

}
