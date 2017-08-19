package ru.projects.favourites.rest.resources;

import java.time.LocalDate;
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
import ru.projects.favourites.domain.User;

@SuppressWarnings("deprecation")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = Inclusion.NON_NULL)
public class UserResource extends DomainResource {

	private static final long serialVersionUID = -6638828186291089421L;

	@NotNull
	@NotEmpty
	private final String email;

	@NotNull
	private final LocalDate regDate;

	@NotNull
	@NotEmpty
	private final String password;

	@NotNull
	private LocalDateTime lastLogged;

	@JsonCreator
	public UserResource(@JsonProperty("uid") String uid, @JsonProperty("deletingDT") LocalDateTime deletingDT,
			@JsonProperty("email") String email, @JsonProperty("lastLogged") LocalDateTime lastLogged,
			@JsonProperty("regDate") LocalDate regDate, @JsonProperty("password") String password) {
		super(uid, deletingDT, deletingDT != null);
		this.email = email;
		this.regDate = regDate;
		this.password = password;
		this.lastLogged = lastLogged;
	}

	public UserResource(User user) {
		super(user);
		this.email = user.getEmail();
		this.regDate = user.getRegDate();
		this.password = user.getPassword();
		this.lastLogged = user.getLastLoggedDT();
	}

	@JsonProperty("email")
	public String getEmail() {
		return this.email;
	}

	@JsonProperty("regDate")
	public LocalDate getRegDate() {
		return this.regDate;
	}

	@JsonProperty("lastLogged")
	public LocalDateTime getLastLogged() {
		return this.lastLogged;
	}

	@JsonIgnore
	public String getPassword() {
		return this.password;
	}
	
	public void loggingLogin() {
		this.lastLogged = LocalDateTime.now();
	}

	@Override
	public DomainObject convertToDomainObject(boolean isNew, String username) {
		final User user;
		if (isNew) {
			user = new User(getUID(), this.email, this.password);
		} else {
			user = new User(getUID(), getDeletingDT(), password);
			user.setEmail(getEmail());
			user.setRegDate(getRegDate());
			user.setLastLoggedDT(getLastLogged());
		}

		return user;
	}
}
