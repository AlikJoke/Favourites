package ru.projects.favourites.rest.controllers;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import ru.projects.favourites.rest.resources.DomainResource;

public interface RESTOperations {

	static final String BASE_URI = "/favourites/{entityType}";
	static final String RESOURCE_URI = "/favourites/{entityType}/{uid}";

	@Null
	DomainResource readById(@NotNull String id, @NotNull String entityType);

	@NotNull
	List<DomainResource> readAll(@NotNull String entityType);

	void create(@NotNull String entityType, @NotNull DomainResource resource);

	@NotNull
	DomainResource update(@NotNull String entityType, @NotNull DomainResource resource);

	void delete(@NotNull String entityType, @NotNull String uid);
}
