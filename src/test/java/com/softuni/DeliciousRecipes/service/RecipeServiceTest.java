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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
    private UserEntity testUser;
    @Captor
    private ArgumentCaptor<Recipe> captor = ArgumentCaptor.forClass(Recipe.class);

    @BeforeEach
    public void setUp() {
        this.recipeServiceToTest = new RecipeService(
                recipeRepositoryMock,
                new ModelMapper(),
                userRepositoryMock,
                userServiceMock,
                categoryServiceTestMock);

        testUser = new UserEntity();
        testUser.setUsername("testUser");
    }

    @Test
    public void testAddNotExistingRecipeToFavorite() {
        when(userServiceMock.getLoggedUser())
                .thenReturn(testUser);

        recipeServiceToTest.addToFavorites(2L);
        Assertions.assertEquals(0, testUser.getFavoriteRecipes().size());
    }

    @Test
    public void testAddAlreadyAddedRecipeToFavorites() {
        Recipe recipe = new Recipe();
        recipe.setName("Salad");
        recipe.setId(1L);
        testUser.getFavoriteRecipes().add(recipe);

        when(userServiceMock.getLoggedUser())
                .thenReturn(testUser);

        when(recipeRepositoryMock
                .findById(recipe.getId()))
                .thenReturn(Optional.of(recipe));

        recipeServiceToTest.addToFavorites(recipe.getId());

        Assertions.assertEquals(1, testUser.getFavoriteRecipes().size());
    }

    @Test
    public void testAddRecipeToFavorites_Success() {
        Recipe recipe1 = new Recipe();
        recipe1.setName("Salad");
        recipe1.setId(1L);

        Recipe recipe2 = new Recipe();
        recipe2.setName("Soup");
        recipe2.setId(2L);

        testUser.getFavoriteRecipes().add(recipe1);

        when(userServiceMock.getLoggedUser())
                .thenReturn(testUser);

        when(recipeRepositoryMock
                .findById(recipe2.getId()))
                .thenReturn(Optional.of(recipe2));

        recipeServiceToTest.addToFavorites(recipe2.getId());

        Assertions.assertEquals(2, testUser.getFavoriteRecipes().size());
    }

//    @Test
//    public void testLikeRecipe_Success() {
//        Recipe recipe1 = new Recipe();
//        recipe1.setName("Salad");
//        recipe1.setId(1L);
//
//        Recipe recipe2 = new Recipe();
//        recipe2.setName("Soup");
//        recipe2.setId(2L);
//
//        testUser.getFavoriteRecipes().add(recipe1);
//
//        when(userServiceMock.getLoggedUser())
//                .thenReturn(testUser);
//
//        when(recipeRepositoryMock
//                .findById(recipe2.getId()))
//                .thenReturn(Optional.of(recipe2));
//
//        recipeServiceToTest.addToFavorites(recipe2.getId());
//
//        Assertions.assertEquals(2, testUser.getFavoriteRecipes().size());
//    }


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
    void testAddRecipe_Success() {
        when(userServiceMock.getLoggedUser())
                .thenReturn(testUser);

        RecipeAddDTO recipeAddDTO = new RecipeAddDTO(
                "Cake",
                CategoryName.DESSERT,
                "test ingredients",
                "mix the products",
                10,
                "picture");

        Category category = new Category();
        category.setName(CategoryName.DESSERT);
        category.setId(1L);

        when(categoryServiceTestMock.findByName(CategoryName.DESSERT)
        ).thenReturn(category);

        recipeServiceToTest.add(recipeAddDTO);

        verify(recipeRepositoryMock).save(captor.capture());
    }


}



