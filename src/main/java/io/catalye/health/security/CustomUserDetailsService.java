package io.catalye.health.security;


import java.util.Optional;

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

    @Autowired
    UserRepo userRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        // Let people login with either user name or email
        User user = userRepo.findByEmail(username);
        return UserPrincipal.create(user);
    }

//    public UserDetails findById(String userId) {
//         User user = userRepo.findById(userId);
//        return UserPrincipal.create(user);
//    }
}
