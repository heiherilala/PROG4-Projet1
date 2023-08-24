package com.hei.project2p1.service;

import com.hei.project2p1.repository.firm.UserRepository;
import com.hei.project2p1.repository.firm.entity.UsersEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersEntity users = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(
                users.getUsername(),
                users.getPassword(),
                users.isEnabled(),
                true,
                true,
                true,
                Collections.emptyList()
        );
    }
}
