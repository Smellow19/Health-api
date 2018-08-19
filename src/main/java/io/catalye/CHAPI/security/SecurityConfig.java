package io.catalye.CHAPI.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		

	// Add our users for in memory authentication
		
	UserBuilder users = User.withDefaultPasswordEncoder();
	
	auth.inMemoryAuthentication()
		.withUser(users.username("john").password("test").roles("EMPLOYEE"))
		.withUser(users.username("mary").password("test").roles("MANAGER"))
		.withUser(users.username("susan").password("test").roles("ADMIN"));
		
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("http://localhost:3000/")
		.loginProcessingUrl("/user/authenticate")
		.permitAll();
	}
}
