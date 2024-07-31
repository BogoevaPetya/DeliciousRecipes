package com.softuni.DeliciousRecipes.service;

import com.softuni.DeliciousRecipes.model.dto.RecipeAddDTO;
import com.softuni.DeliciousRecipes.model.dto.RecipeShortInfoDTO;
import com.softuni.DeliciousRecipes.model.entity.Category;
import com.softuni.DeliciousRecipes.model.entity.Recipe;
import com.softuni.DeliciousRecipes.model.entity.UserEntity;
import com.softuni.DeliciousRecipes.model.enums.CategoryName;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {
    private RecipeService recipeServiceToTest;
    @Mock
    private RecipeRepository recipeRepositoryMock;
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private UserService userServiceMock;
    @Mock
    private CategoryService categoryServiceTestMock;
    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp(){
        this.recipeServiceToTest = new RecipeService(
                        recipeRepositoryMock,
                        new ModelMapper(),
                        userRepositoryMock,
                        userServiceMock,
                categoryServiceTestMock);
    }

    @Test
    void testAddRecipe(){
        RecipeAddDTO recipeAddDTO = new RecipeAddDTO();

        UserEntity user = new UserEntity();
        Recipe recipe = new Recipe();
        Category category = new Category();

        when(userServiceMock.findUserByUsername("testUser")).thenReturn(Optional.of(user));
        when(categoryServiceTestMock.findByName(CategoryName.DESSERT)).thenReturn(Optional.of(category));

        boolean present = verify(Optional.of(category)).isPresent();
        Assertions.assertTrue(present);

        boolean result = recipeServiceToTest.add(recipeAddDTO, "testUser");

        Assertions.assertTrue(result);

        assertEquals(user, recipe.getAddedBy());
        assertEquals(category, recipe.getCategory());

    }

    @Test
    public void testGetAllRecipesByCategory() {
        Recipe recipe1 = new Recipe();
        recipe1.setName("Cheesecake");

        Recipe recipe2 = new Recipe();
        recipe1.setName("Brownie");

        RecipeShortInfoDTO recipeDTO1 = new RecipeShortInfoDTO();
        RecipeShortInfoDTO recipeDTO2 = new RecipeShortInfoDTO();

        CategoryName categoryName = CategoryName.DESSERT;
        when(recipeRepositoryMock.findByCategoryName(categoryName)).thenReturn(Arrays.asList(recipe1, recipe2));

        List<RecipeShortInfoDTO> result = recipeServiceToTest.getAllRecipesByCategory(categoryName);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testAddToFavorites() {
        Recipe recipe = new Recipe();
        recipe.setName("Salad");
        recipe.setId(1L);
        recipeRepositoryMock.save(recipe);

        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        List<Recipe> favorites = new ArrayList<>();
        user.setFavoriteRecipes(favorites);

        when(userServiceMock.getLoggedUsername()).thenReturn("testUser");
        when(recipeRepositoryMock.findById(1L)).thenReturn(Optional.of(recipe));


        recipeServiceToTest.addToFavorites(recipe.getId());


        Optional<UserEntity> optionalUser = userRepositoryMock.findById(user.getId());
        UserEntity updatedUser = optionalUser.get();

        assertFalse(updatedUser.getFavoriteRecipes().isEmpty());
        assertEquals(1L, updatedUser.getFavoriteRecipes().size());
        assertEquals(recipe.getId(), updatedUser.getFavoriteRecipes().iterator().next().getId());
    }
}
