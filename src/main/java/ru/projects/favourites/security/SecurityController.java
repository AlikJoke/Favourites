package ru.projects.favourites.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.projects.favourites.rest.resources.UserResource;

@Controller
public class SecurityController {

	@Autowired
	private SecurityReference security;

	@PostMapping(SecurityReferenceImpl.PATH_LOGIN)
	public void login(@RequestParam("username") String username, @RequestParam("password") String password) {
		security.login(username, password);
	}

	@GetMapping(SecurityReferenceImpl.PATH_LOGOUT)
	@ResponseStatus(HttpStatus.OK)
	public void logout() {
		security.logout();
	}

	@PostMapping(SecurityReferenceImpl.PATH_REGISTER)
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody UserResource user) {
		security.create(user);
	}
}
