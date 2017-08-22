package ru.projects.favourites.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;

import ru.projects.favourites.dao.DomainOperations;
import ru.projects.favourites.domain.User;

@Service
public class FacebookConnectionSignUp implements ConnectionSignUp {

	@Autowired
	private DomainOperations operations;

	@Override
	public String execute(Connection<?> arg0) {
		UserProfile profile = arg0.fetchUserProfile();
		User user = new User(profile.getUsername(), profile.getEmail(), profile.getId());
		operations.save(user);

		return user.getUID();
	}

}
