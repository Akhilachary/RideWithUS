package com.robo.RideWithUs.security;

import com.robo.RideWithUs.Entity.User;
import com.robo.RideWithUs.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String mobile)
            throws UsernameNotFoundException {

        User user = userRepository
                .findByMobileNumber(Long.parseLong(mobile))
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with mobile: " + mobile
                        )
                );

        return new org.springframework.security.core.userdetails.User(
                String.valueOf(user.getMobileNumber()), // username
                user.getPassword(),                     // BCrypt password
                List.of(new SimpleGrantedAuthority(
                        "ROLE_" + user.getRole()         // ROLE_CUSTOMER / ROLE_DRIVER
                ))
        );
    }
}
