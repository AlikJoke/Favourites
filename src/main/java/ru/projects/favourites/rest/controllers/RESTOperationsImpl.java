package ru.projects.favourites.rest.controllers;

import java.util.List;

import org.springframework.stereotype.Service;

import ru.projects.favourites.rest.resources.DomainResource;

@Service
public class RESTOperationsImpl implements RESTOperations {
	
	@Override
	public DomainResource readById(String id, String entityType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DomainResource> readAll(String entityType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(String entityType, DomainResource resource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DomainResource update(String entityType, DomainResource resource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String entityType, String uid) {
		// TODO Auto-generated method stub
		
	}

}
