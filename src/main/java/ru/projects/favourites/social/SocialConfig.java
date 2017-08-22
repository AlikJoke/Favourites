package ru.projects.favourites.social;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SocialAuthenticationServiceLocator;
import org.springframework.social.security.SocialAuthenticationServiceRegistry;

import ru.projects.favourites.configs.DbConfiguration;

@Configuration
@EnableAutoConfiguration
@EnableSocial
@Import(DbConfiguration.class)
public class SocialConfig implements SocialConfigurer {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private Environment env;

	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer,
			Environment environment) {
		connectionFactoryConfigurer.addConnectionFactory(
				new FacebookConnectionFactory(environment.getProperty("spring.social.facebook.appId"),
						environment.getProperty("spring.social.facebook.appSecret")));
	}

	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
				connectionFactoryLocator, Encryptors.noOpText());
		repository.setConnectionSignUp(new FacebookConnectionSignUp());
		return repository;
	}

	@Bean
	public SocialAuthenticationServiceLocator connectionFactoryLocator() {
		SocialAuthenticationServiceRegistry registry = new SocialAuthenticationServiceRegistry();
		registry.addConnectionFactory(new FacebookConnectionFactory(env.getProperty("spring.social.facebook.appId"),
				env.getProperty("spring.social.facebook.appSecret")));
		return registry;
	}

	@Bean
	public UsersConnectionRepository usersConnectionRepository() {
		return getUsersConnectionRepository(connectionFactoryLocator());
	}

	@Bean
	public ConnectInterceptor<Facebook> facebookConnectInterceptor() {
		return new FacebookConnectInterceptor();
	}
	
	@Bean
	public ProviderSignInUtils providerSignIn() {
		return new ProviderSignInUtils(connectionFactoryLocator(), usersConnectionRepository());
	}

	@Bean
	public ConnectController connectController() {
		ConnectController controller = new ConnectController(connectionFactoryLocator(), connectionRepository());
		controller.addInterceptor(facebookConnectInterceptor());
		return controller;
	}

	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public ConnectionRepository connectionRepository() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null)
			throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");

		return usersConnectionRepository().createConnectionRepository(authentication.getName());
	}
}
