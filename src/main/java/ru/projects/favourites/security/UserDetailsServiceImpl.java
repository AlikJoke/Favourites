package ru.projects.favourites.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

import ru.projects.favourites.dao.DomainOperations;
import ru.projects.favourites.domain.EntityType;
import ru.projects.favourites.domain.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, SocialUserDetailsService {

	@Autowired
	private DomainOperations domainOperations;

	private final List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));

	private final List<GrantedAuthority> authoritiesFb = Arrays.asList(new SimpleGrantedAuthority("facebook_user"));

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = domainOperations.findById(EntityType.USER, username);
		if (user == null)
			throw new UsernameNotFoundException(String.format("Not found user with username '%s'", username));

		return new org.springframework.security.core.userdetails.User(username, user.getPassword(),
				Collections.unmodifiableCollection(authorities));
	}

	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		User user = domainOperations.findById(EntityType.USER, userId);
		if (user == null)
			throw new UsernameNotFoundException(String.format("Not found user with username '%s'", userId));

		return new SocialUser(userId, user.getPassword(), Collections.unmodifiableCollection(authoritiesFb));
	}

}
