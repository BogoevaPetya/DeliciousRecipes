package com.softuni.DeliciousRecipes.service;

import com.softuni.DeliciousRecipes.model.dto.RecipeAddDTO;
import com.softuni.DeliciousRecipes.model.dto.RecipeShortInfoDTO;
import com.softuni.DeliciousRecipes.model.entity.Category;
import com.softuni.DeliciousRecipes.model.entity.Recipe;
import com.softuni.DeliciousRecipes.model.entity.UserEntity;
import com.softuni.DeliciousRecipes.model.enums.CategoryName;
import com.softuni.DeliciousRecipes.repository.RecipeRepository;
import com.softuni.DeliciousRecipes.repository.UserRepository;
import com.softuni.DeliciousRecipes.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
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
        recipe1.setName("Cake");
        recipe1.setId(1L);

        Recipe recipe2 = new Recipe();
        recipe2.setName("Ice cream");
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

    @Test
    public void testLikeAlreadyLikedRecipe() {
        Recipe recipe1 = new Recipe();
        recipe1.setName("Cake");
        recipe1.setId(1L);


        testUser.getLikedRecipes().add(recipe1);

        when(userServiceMock.getLoggedUser())
                .thenReturn(testUser);

        when(recipeRepositoryMock
                .findById(recipe1.getId()))
                .thenReturn(Optional.of(recipe1));

        recipeServiceToTest.likeRecipe(recipe1.getId());

        Assertions.assertEquals(1, testUser.getLikedRecipes().size());
    }

    @Test
    public void testLikeRecipe_Success() {
        Recipe recipe1 = new Recipe();
        recipe1.setName("Cake");
        recipe1.setId(1L);

        when(userServiceMock.getLoggedUser())
                .thenReturn(testUser);

        when(recipeRepositoryMock
                .findById(recipe1.getId()))
                .thenReturn(Optional.of(recipe1));

        recipeServiceToTest.likeRecipe(recipe1.getId());

        Assertions.assertEquals(1, testUser.getLikedRecipes().size());
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
    void testGetRecipeById_Success(){
        Recipe recipe1 = new Recipe();
        recipe1.setName("Cheesecake");
        recipe1.setId(1L);

        when(recipeRepositoryMock.findById(recipe1.getId())).thenReturn(Optional.of(recipe1));

        recipeServiceToTest.getRecipeById(1L);

        Assertions.assertEquals("Cheesecake", recipe1.getName());
    }

    @Test
    void testGetRecipeByNotExistingId(){
        Assertions.assertThrows(
                ObjectNotFoundException.class, () -> recipeServiceToTest.getRecipeById(2L));
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

        recipeServiceToTest.deleteRecipe(1L);

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

        recipeServiceToTest.removeFromFavorites(1L);
        Assertions.assertEquals(0, user.getFavoriteRecipes().size());
    }


}



