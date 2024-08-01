package com.softuni.DeliciousRecipes.service;

import com.softuni.DeliciousRecipes.model.entity.Recipe;
import com.softuni.DeliciousRecipes.model.entity.UserEntity;
import com.softuni.DeliciousRecipes.repository.RecipeRepository;
import com.softuni.DeliciousRecipes.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HomeServiceTest {
    private HomeService homeServiceToTest;
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private RecipeRepository recipeRepositoryMock;
    @Mock
    private UserService userServiceMock;

    @BeforeEach
    public void setUp() {
        this.homeServiceToTest = new HomeService(userRepositoryMock, recipeRepositoryMock, userServiceMock);
    }

    @Test
    void testDeleteRecipeById(){
        UserEntity user1 = new UserEntity();
        user1.setUsername("Pesho");

        UserEntity user2 = new UserEntity();
        user2.setUsername("Gosho");

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        user1.getFavoriteRecipes().add(recipe);
        user2.getLikedRecipes().add(recipe);

        List<UserEntity> allUsers = new ArrayList<>();
        allUsers.add(user1);
        allUsers.add(user2);

        when(userRepositoryMock.findAll()).thenReturn(allUsers);

        homeServiceToTest.deleteRecipe(1L);

        Assertions.assertEquals(0, user1.getFavoriteRecipes().size());
        Assertions.assertEquals(0, user2.getLikedRecipes().size());
    }

    @Test
    void testRemoveRecipeFromFavorites() {
        UserEntity user = new UserEntity();
        user.setUsername("Pesho");

        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Moussaka");

        user.getFavoriteRecipes().add(recipe);

        when(userServiceMock.getLoggedUser()).thenReturn(user);
        when(recipeRepositoryMock.findById(1L)).thenReturn(Optional.of(recipe));

        homeServiceToTest.removeFromFavorites(1L);
        Assertions.assertEquals(0, user.getFavoriteRecipes().size());
    }
}
