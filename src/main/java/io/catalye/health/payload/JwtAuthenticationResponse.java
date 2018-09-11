package io.catalye.health.payload;

import java.util.List;

public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String email;
    private List<String> roles;
    

    public JwtAuthenticationResponse(String accessToken, String email, List<String> roles) {
        this.accessToken = accessToken;
        this.email = email;
        this.roles = roles;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    
    public String getEmail() {
        return email;
    }

    
    public List<String> getRoles() {
        return roles;
    }

    
    public void setEmail(String email) {
        this.email = email;
    }

    
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    
    
}