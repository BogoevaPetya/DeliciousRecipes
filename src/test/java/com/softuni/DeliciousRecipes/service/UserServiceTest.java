package com.softuni.DeliciousRecipes.service;

import com.softuni.DeliciousRecipes.model.dto.UserInfoDTO;
import com.softuni.DeliciousRecipes.model.dto.UserRegisterDTO;
import com.softuni.DeliciousRecipes.model.entity.Role;
import com.softuni.DeliciousRecipes.model.entity.UserEntity;
import com.softuni.DeliciousRecipes.model.enums.UserRole;
import com.softuni.DeliciousRecipes.repository.RoleRepository;
import com.softuni.DeliciousRecipes.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private UserService userServiceToTest;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Captor
    private ArgumentCaptor<UserEntity> userEntityCaptor;

    @BeforeEach
    public void setUp(){
        this.userServiceToTest =
                new UserService(
                        mockUserRepository,
                        new ModelMapper(),
                        mockRoleRepository,
                        mockPasswordEncoder);
    }

    @Test
    public void testRegisterUser_Successful() {
        //Arrange
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("test");
        userRegisterDTO.setEmail("test@test.bg");
        userRegisterDTO.setPassword("secret");

        when(mockPasswordEncoder.encode(userRegisterDTO.getPassword()))
                .thenReturn(userRegisterDTO.getPassword()+userRegisterDTO.getPassword());

        //Act
        userServiceToTest.registerUser(userRegisterDTO);

        //Assert
        verify(mockUserRepository).save(userEntityCaptor.capture());

        UserEntity actualSavedEntity = userEntityCaptor.getValue();

        Assertions.assertNotNull(actualSavedEntity);
        Assertions.assertEquals(userRegisterDTO.getUsername(), actualSavedEntity.getUsername());
        Assertions.assertEquals(userRegisterDTO.getEmail(), actualSavedEntity.getEmail());
        Assertions.assertEquals(userRegisterDTO.getPassword() + userRegisterDTO.getPassword(), actualSavedEntity.getPassword());

    }



    @Test
    public void testFindUserByUsername() {
        String username = "test";
        UserEntity user = new UserEntity();
        user.setUsername(username);

        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Optional<UserEntity> result = userServiceToTest.findUserByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
    }
}
