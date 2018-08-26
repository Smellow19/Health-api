package io.catalye.CHAPI.payload;

import javax.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//This takes the login request and validates it 
public class LoginRequest {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginRequest.class);

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
    	logger.warn(email);
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
    	logger.warn(password);
        this.password = password;
    }

}