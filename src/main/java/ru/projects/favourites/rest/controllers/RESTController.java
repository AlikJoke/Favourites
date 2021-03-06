package ru.projects.favourites.rest.controllers;

import java.util.List;

import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ru.projects.favourites.rest.resources.DomainResource;
import ru.projects.favourites.user.CurrentUser;

@RestController
public class RESTController {

	@Autowired
	private RESTOperations operations;

	@Autowired
	private Provider<CurrentUser> currentUser;

	@GetMapping(RESTOperations.RESOURCE_URI)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public DomainResource readById(@PathVariable("entityType") String entityType, @PathVariable("uid") String uid) {
		return operations.readById(entityType, uid);
	}

	@GetMapping(RESTOperations.BASE_URI)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<DomainResource> readAll(@PathVariable("entityType") String entityType) {
		return operations.readAll(entityType, currentUser.get().getUsername());
	}

	@PostMapping(RESTOperations.BASE_URI)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void create(@PathVariable("entityType") String entityType, @RequestBody DomainResource resource) {
		operations.create(entityType, resource, currentUser.get().getUsername());
	}

	@RequestMapping(value = RESTOperations.RESOURCE_URI, method = RequestMethod.PUT)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public DomainResource update(@PathVariable("entityType") String entityType, @RequestBody DomainResource resource) {
		return operations.update(entityType, resource);
	}

	@RequestMapping(value = RESTOperations.RESOURCE_URI, method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("entityType") String entityType, @PathVariable("uid") String uid) {
		operations.delete(entityType, uid);
	}

	@RequestMapping(value = RESTOperations.BASE_URI, method = RequestMethod.OPTIONS)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public void baseOptions(@PathVariable("entityType") String entityType, HttpServletRequest request,
			HttpServletResponse response) {
		response.addHeader("Allow", "POST, GET, OPTIONS");
	}

	@RequestMapping(value = RESTOperations.RESOURCE_URI, method = RequestMethod.OPTIONS)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public void resourceOptions(@PathVariable("entityType") String entityType, @PathVariable("uid") String uid,
			HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Allow", "PUT, GET, DELETE, OPTIONS");
	}
}
