package io.catalye.CHAPI.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.catalye.CHAPI.security.CustomUserDetailsService;
import io.catalye.CHAPI.security.JwtAuthenticationEntryPoint;
import io.catalye.CHAPI.security.JwtAuthenticationFilter;

@Configuration
//This Enables the websecurity for the project
@EnableWebSecurity
//This enables the method level security for using the @Preauthorize annotation
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)

//the extension allows for defaulty security configurations and security customization
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

	
	//This loads users based on username or email
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    //This is used to return an error if a user tries t o access a resource without logging in.
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    //This will read the JWT token and validate it as well as load the user details.
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    //this will authenticate a user as well as get the roles
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    	logger.warn(customUserDetailsService + " ");
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    //This will interface used to create the authentication manager 
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //This method helps configure rules of accessibility and use of CRUD within the website.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .and()
                .csrf()
                    .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(unauthorizedHandler)
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .authorizeRequests()
                    .antMatchers("/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                        .permitAll()
                    .antMatchers("/api/auth/signin")
                        .permitAll()
                        .antMatchers("/user/encode")
                        .permitAll()
                    .anyRequest()
                        .authenticated();

        // Add our custom JWT security filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}