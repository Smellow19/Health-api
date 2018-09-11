package io.catalye.health;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * This customizes web security for the application
 * 
 * @author tBridges
 *
 */
@Configuration
public class WebSecurity extends WebMvcConfigurerAdapter {

    /**
     * This prevents Cors conflicts while working on a local server
     * 
     * @return
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("HEAD", "GET", "PUT",
                        "POST", "DELETE", "PATCH");
            }
        };
    }
    
    
}