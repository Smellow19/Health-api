package io.catalye.CHAPI.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.catalye.CHAPI.controller.UserController;
import io.catalye.CHAPI.domain.User;
import io.catalye.CHAPI.repositories.UserRepo;

//Loads a user via the users given email or username
@Service
public class CustomUserDetailsService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);


    @Autowired
    UserRepo userRepository;

    // Allows users to log in using their email.
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email);
        logger.warn(email);
        logger.warn(user.toString());
        return UserPrincipal.create(user);
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return UserPrincipal.create(user);
    }
}
