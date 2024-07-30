package com.softuni.DeliciousRecipes.service;

import com.softuni.DeliciousRecipes.model.dto.UserDetailsDTO;
import com.softuni.DeliciousRecipes.model.entity.Role;
import com.softuni.DeliciousRecipes.model.entity.UserEntity;
import com.softuni.DeliciousRecipes.model.enums.UserRole;
import com.softuni.DeliciousRecipes.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {
    private UserDetailsService toTest;
    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp(){
        toTest = new UserDetailsService(mockUserRepository);
    }

    @Test
    void testLoadUserByUsername_UserFound(){
        //Arrange
        UserEntity testUser = new UserEntity();
        testUser.setUsername("test");
        testUser.setEmail("test@test.bg");
        testUser.setPassword("secret");

        List<Role> roles = new ArrayList<>();
        Role role1 = new Role();
        role1.setRole(UserRole.ADMINISTRATOR);
        Role role2 = new Role();
        role2.setRole(UserRole.USER);
        roles.add(role1);
        roles.add(role2);

        testUser.setRoles(roles);

        when(mockUserRepository.findByUsername("test"))
                .thenReturn(Optional.of(testUser));

        // Act
        UserDetails userDetails = toTest.loadUserByUsername("test");

        // Assert
        assertInstanceOf(UserDetailsDTO.class, userDetails);

        UserDetailsDTO userDetailsDTO = (UserDetailsDTO) userDetails;

        assertEquals(testUser.getUsername(), userDetailsDTO.getUsername());
        assertEquals(testUser.getPassword(), userDetailsDTO.getPassword());

        List<String> expectedRoles = testUser.getRoles().stream().map(Role::getRole).map(r -> "ROLE_" + r).toList();
        List<String> actualRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        assertEquals(expectedRoles, actualRoles);
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        assertThrows(UsernameNotFoundException.class,
                () -> toTest.loadUserByUsername("abc"));
    }
}
