package com.softuni.DeliciousRecipes.service;

import com.softuni.DeliciousRecipes.model.entity.Role;
import com.softuni.DeliciousRecipes.model.entity.UserEntity;
import com.softuni.DeliciousRecipes.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.stream.Collectors;

public class LoginDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public LoginDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        UserDetails userDetails = user
                .map(LoginDetailsService::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));
        return userDetails;
    }

    private static UserDetails map(UserEntity user) {
        return new User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(LoginDetailsService::mapRole).collect(Collectors.toList())
        );
    }

    private static GrantedAuthority mapRole(Role role) {
        return new SimpleGrantedAuthority(
                "ROLE_" + role.getRole().name()
        );
    }

}