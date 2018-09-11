package io.catalye.health.security;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.catalye.health.domain.User;
import io.catalye.health.repositories.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    UserRepo userRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {        
        Optional<User> u = userRepo.findByEmail(email);
        logger.warn("USER PRINCIPAL: " + u.toString());
        if (u.isPresent()) return UserPrincipal.create(u.get());
        else throw new UsernameNotFoundException("User not found with email: " + email);
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(String userId) {
        User user = userRepo.findById(userId).orElseThrow(
            () -> new UsernameNotFoundException("User not found with id : " + userId)
        );

        return UserPrincipal.create(user);
    }
}