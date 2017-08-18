package ru.projects.favourites.rest.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import ru.projects.favourites.dao.DomainOperations;
import ru.projects.favourites.domain.EntityType;
import ru.projects.favourites.domain.Favourite;
import ru.projects.favourites.rest.resources.DomainResource;
import ru.projects.favourites.rest.resources.FavouriteResource;

@Service
public class RESTOperationsImpl implements RESTOperations {

	@Autowired
	private DomainOperations domainOperations;

	@Override
	public List<DomainResource> readAll(String entityType, String username) {
		if (EntityType.value(entityType) != EntityType.FAVOURITE)
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);

		return domainOperations.findAll(username).stream().map(fv -> new FavouriteResource((Favourite) fv))
				.collect(Collectors.toList());
	}

	@Override
	public void create(String entityType, DomainResource resource, String username) {
		domainOperations.save(resource.convertToDomainObject(true, username));
	}

	@Override
	public void delete(String entityType, String uid) {
		domainOperations.remove(EntityType.value(entityType), uid);
	}

	@Override
	public DomainResource update(String entityType, DomainResource resource) {
		domainOperations.update(EntityType.value(entityType), resource.convertToDomainObject(false, null));
		return this.readById(resource.getUID(), entityType);
	}

	@Override
	public DomainResource readById(String id, String entityType) {
		return DomainResource.convertToResource(domainOperations.findById(EntityType.value(entityType), id));
	}

}
