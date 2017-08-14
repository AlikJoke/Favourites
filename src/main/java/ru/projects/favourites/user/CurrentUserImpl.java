package ru.projects.favourites.user;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component("currentUser")
@Scope("session")
public class CurrentUserImpl implements CurrentUser {

	@Override
	public String getUsername() {
		return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
	}

}
