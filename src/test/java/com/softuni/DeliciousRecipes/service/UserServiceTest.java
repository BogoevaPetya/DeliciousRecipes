package com.softuni.DeliciousRecipes.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.softuni.DeliciousRecipes.model.dto.FoodInfoDTO;
import com.softuni.DeliciousRecipes.model.dto.UserInfoDTO;
import com.softuni.DeliciousRecipes.model.dto.UserRegisterDTO;
import com.softuni.DeliciousRecipes.model.entity.Recipe;
import com.softuni.DeliciousRecipes.model.entity.Role;
import com.softuni.DeliciousRecipes.model.entity.UserEntity;
import com.softuni.DeliciousRecipes.model.enums.UserRole;
import com.softuni.DeliciousRecipes.repository.RoleRepository;
import com.softuni.DeliciousRecipes.repository.UserRepository;
import net.bytebuddy.asm.Advice;
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

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    @Mock
    private ModelMapper modelMapper;


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
        userRegisterDTO.setConfirmPassword("secret");

        when(mockPasswordEncoder.encode(userRegisterDTO.getPassword()))
                .thenReturn(userRegisterDTO.getPassword()+userRegisterDTO.getPassword());

        Role role = new Role();
        role.setRole(UserRole.USER);
        when(mockRoleRepository.findByRole(UserRole.USER))
                .thenReturn(role);
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
    public void testRegisterUser_passwordsMismatch() {
        //Arrange
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("test");
        userRegisterDTO.setEmail("test@test.bg");
        userRegisterDTO.setPassword("topSecret");
        userRegisterDTO.setConfirmPassword("secret");

        Role role = new Role();
        role.setRole(UserRole.USER);

        //Act
        boolean registered = userServiceToTest.registerUser(userRegisterDTO);

        //Assert
        Assertions.assertFalse(registered);
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

    @Test
    public void testGetUserDetails(){
        Long userId = 1L;

        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUsername("test");
        user.setFavoriteRecipes(List.of());
        user.setAddedRecipes(Set.of());
        user.setLikedRecipes(List.of());

        when(mockUserRepository.findById(userId)).thenReturn(Optional.of(user));

        UserInfoDTO userInfoDTO = userServiceToTest.getUserDetails(userId);

        assertNotNull(userInfoDTO);
        verify(mockUserRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetUserDetails_userNotFound(){
       assertNull( userServiceToTest.getUserDetails(2L));
    }

    @Test
    void testMap(){
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("test");
        userRegisterDTO.setEmail("test@test.bg");
        userRegisterDTO.setPassword("secret");

        Role role = new Role();
        role.setRole(UserRole.USER);
        userRegisterDTO.setRoles(List.of(role));

        when(mockRoleRepository.findByRole(UserRole.USER))
                .thenReturn(role);

        userServiceToTest.map(userRegisterDTO);

        Assertions.assertEquals("test", userRegisterDTO.getUsername());
    }

    @Test
    void testGetLoggedUser(){
        UserEntity user = mock(UserEntity.class);
        user.setId(1L);
        user.setUsername("test");

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(mockUserRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        userServiceToTest.getLoggedUser();

        Assertions.assertEquals(user.getUsername(), userServiceToTest.getLoggedUser().getUsername());

    }

}
