package ru.projects.favourites.mail;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public interface MailService {

	void send(@NotNull @NotEmpty @Email String email, @NotNull @NotEmpty String theme, @Null String text);
}
