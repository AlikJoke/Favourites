package ru.projects.favourites.rest.resources;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

import ru.projects.favourites.domain.DomainObject;
import ru.projects.favourites.domain.Favourite;

@SuppressWarnings("deprecation")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = Inclusion.NON_NULL)
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

	public FavouriteResource(Favourite fv) {
		super(fv);
		this.name = fv.getName();
		this.addingDT = fv.getAddingDT();
		this.link = fv.getLink();
		this.order = fv.getOrder();
		this.counter = fv.getCounter();
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

	@JsonIgnore
	@Override
	public DomainObject convertToDomainObject(boolean isNew, String username) {
		final Favourite fv;
		if (isNew)
			fv = new Favourite(this.link, this.name, null);
		else
			fv = new Favourite(this.name, this.link, getUID(), username, this.addingDT, getDeletingDT());
		return fv;
	}

}
