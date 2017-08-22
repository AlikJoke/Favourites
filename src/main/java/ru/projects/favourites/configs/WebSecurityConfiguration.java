package ru.projects.favourites.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import ru.projects.favourites.social.SocialConfig;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@Import(SocialConfig.class)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/VAADIN/**", "/login", "/login/**", "/error/**", "/accessDenied/**", "/vaadinServlet/**")
				.permitAll().and().formLogin().loginPage("/login").failureUrl("/login?error")
				.usernameParameter("username").permitAll().and().logout().logoutUrl("/logout")
				.deleteCookies("JSESSIONID").deleteCookies("remember-me").logoutSuccessUrl("/").permitAll().and()
				.rememberMe().and().apply(new SpringSocialConfigurer().postLoginUrl("/").alwaysUsePostLoginUrl(true));
		;
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
}