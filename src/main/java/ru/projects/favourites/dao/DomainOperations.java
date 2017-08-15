package ru.projects.favourites.dao;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.hibernate.validator.constraints.NotEmpty;

import ru.projects.favourites.domain.DomainObject;

public interface DomainOperations<T extends DomainObject> {

	void save(@NotNull T domain);

	void update(@NotNull T domain, @NotNull @NotEmpty String field, @NotNull Object fieldValue);

	boolean remove(@NotNull T entity);

	@Null
	T findById(boolean isFavourite, @NotNull String uid);

	@NotNull
	List<? extends T> findAll(@NotNull @NotEmpty String username);
}
