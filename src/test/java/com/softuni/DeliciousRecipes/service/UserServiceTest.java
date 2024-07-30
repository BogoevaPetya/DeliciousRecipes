package com.softuni.DeliciousRecipes.service;

import com.softuni.DeliciousRecipes.model.dto.UserInfoDTO;
import com.softuni.DeliciousRecipes.model.entity.UserEntity;
import com.softuni.DeliciousRecipes.repository.RoleRepository;
import com.softuni.DeliciousRecipes.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private UserService toTest;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private ModelMapper mockModelMapper;

    @BeforeEach
    public void setUp(){
        this.toTest = new UserService(mockUserRepository, mockModelMapper, mockRoleRepository, mockPasswordEncoder);
    }


    public UserInfoDTO testRegisterUser(){
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUsername("test");
        userInfoDTO.setEmail("test@test.bg");
        userInfoDTO.setPassword("secret");
        userInfoDTO.setRoles(List.of("USER"));

        return userInfoDTO;
    }

    @Test
    public void testFindUserByUsername() {
        String username = "test";
        UserEntity user = new UserEntity();
        user.setUsername(username);

        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Optional<UserEntity> result = toTest.findUserByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
    }
}
