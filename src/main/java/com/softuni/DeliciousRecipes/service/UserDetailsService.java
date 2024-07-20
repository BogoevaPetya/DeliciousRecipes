package com.softuni.DeliciousRecipes.service;

import com.softuni.DeliciousRecipes.model.dto.UserDetailsDTO;
import com.softuni.DeliciousRecipes.model.entity.Role;
import com.softuni.DeliciousRecipes.model.entity.UserEntity;
import com.softuni.DeliciousRecipes.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        UserDetails userDetails = user
                .map(UserDetailsService::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));
        return userDetails;
    }

    private static UserDetails map(UserEntity user) {
        return new UserDetailsDTO(
                user.getUsername(),
                user.getPassword(),
                mapRole(user.getRoles()),
                user.getId(),
                user.getEmail()
        );
    }

    private static List<GrantedAuthority> mapRole(List<Role> roles) {
        return roles.stream().map(role ->  new SimpleGrantedAuthority(
                "ROLE_" + role.getRole().name())).collect(Collectors.toList());
    }

}