package ru.projects.favourites;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import ru.projects.favourites.mail.MailService;
import ru.projects.favourites.mail.impl.MailProperties;
import ru.projects.favourites.mail.impl.MailServiceImpl;

@Configuration
@PropertySource(value = { "classpath:application.properties" })
public class TestConfiguration {

	@Bean
	public MailService mailService() {
		return new MailServiceImpl();
	}
	
	@Bean
	public MailProperties mailProperties() {
		return new MailProperties();
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfig() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
