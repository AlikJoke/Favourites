package ru.projects.favourites.rest.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.projects.favourites.dao.DomainOperations;
import ru.projects.favourites.domain.DomainObject;
import ru.projects.favourites.domain.Favourite;
import ru.projects.favourites.rest.resources.DomainResource;
import ru.projects.favourites.rest.resources.FavouriteResource;

@Service
public class RESTOperationsImpl implements RESTOperations {

	@Autowired
	private DomainOperations<? extends DomainObject> domainOperations;

	@Override
	public DomainResource readById(String id, String entityType) {
		return null; // TODO
	}

	@Override
	public List<DomainResource> readAll(String entityType, String username) {
		return domainOperations.findAll(username).stream().map(fv -> new FavouriteResource((Favourite) fv))
				.collect(Collectors.toList());
	}
	
	@Override
	public void create(String entityType, DomainResource resource, String username) {
		domainOperations.save(resource.convertToDomainObject(true, username));
	}

	@Override
	public DomainResource update(String entityType, DomainResource resource) {
		// TODO domainOperations.update(resource, field, fieldValue);

		return resource;
	}

	@Override
	public void delete(String entityType, String uid) {
		domainOperations.remove(entityType, uid);
	}

}
