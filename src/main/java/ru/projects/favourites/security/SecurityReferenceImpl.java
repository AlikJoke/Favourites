package ru.projects.favourites.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import ru.projects.favourites.domain.EntityType;
import ru.projects.favourites.rest.controllers.RESTOperations;
import ru.projects.favourites.rest.resources.UserResource;

@Service
public class SecurityReferenceImpl implements SecurityReference {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private RESTOperations operations;
	
	private final static String USER_ENTITY_TYPE = EntityType.USER.getName();

	public final static String PATH_LOGIN = "/login";
	public final static String PATH_LOGOUT = "/logout";
	public final static String PATH_REGISTER = "/register";

	@Override
	public void login(String username, String password) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, password, userDetails.getAuthorities());
		authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		if (usernamePasswordAuthenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			
			final UserResource user = (UserResource) operations.readById(username, USER_ENTITY_TYPE);
			user.loggingLogin();
			operations.update(USER_ENTITY_TYPE, user);
		} else {
			throw new SecurityException("Can't authenticate user!");
		}
	}

	@Override
	public void logout() {
		SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
	}

	@Override
	public void create(UserResource user) {
		operations.create(EntityType.USER.getName(), user, null);
	}

}
