package io.catalye.health.controller;


import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.catalye.health.domain.User;
import io.catalye.health.payload.JwtAuthenticationResponse;
import io.catalye.health.payload.LoginRequest;
import io.catalye.health.repositories.UserRepo;
import io.catalye.health.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
            String encryptedPass = loginRequest.getPassword();
            logger.warn("encrypted pass: " + encryptedPass);
            byte[] decoded = Base64.getMimeDecoder().decode(encryptedPass);
            String decryptPass = new String(decoded);
            
            logger.warn("Decrypted successfully: "+ decryptPass);
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        decryptPass
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        Optional<User> user = userRepo.findByEmail(loginRequest.getEmail());
        List<String> roles = user.get().getRoles();
        logger.warn(roles.get(0));
        logger.warn(user.toString());
        
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, loginRequest.getEmail(), roles ));
    }


};