package io.catalye.CHAPI.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User.UserBuilder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		

	// Add our users for in memory authentication
		
	UserBuilder users = User.withDefaultPasswordEncoder();
	
	auth.inMemoryAuthentication();
		.withUser(user.username("john").password("test").roles("EMPLOYEE"))
		.withUser(user.username("mary").password("test").roles("MANAGER"))
		.withUser(user.username("susan").password("test").roles("ADMIN"))
		
	}
}
