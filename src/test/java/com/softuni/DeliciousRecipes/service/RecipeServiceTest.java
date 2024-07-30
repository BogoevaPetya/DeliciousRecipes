package com.softuni.DeliciousRecipes.service;

import com.softuni.DeliciousRecipes.model.dto.RecipeAddDTO;
import com.softuni.DeliciousRecipes.model.entity.Category;
import com.softuni.DeliciousRecipes.model.entity.Recipe;
import com.softuni.DeliciousRecipes.model.entity.UserEntity;
import com.softuni.DeliciousRecipes.model.enums.CategoryName;
import com.softuni.DeliciousRecipes.repository.RecipeRepository;
import com.softuni.DeliciousRecipes.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

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
    private CategoryService categoryServiceMock;

    @BeforeEach
    public void setUp(){
        this.recipeServiceToTest = new RecipeService(
                        recipeRepositoryMock,
                        new ModelMapper(),
                        userRepositoryMock,
                        userServiceMock,
                        categoryServiceMock);
    }

    @Test
    void testAddRecipe(){
        Optional<UserEntity> optionalUser = userServiceMock.findUserByUsername("Pesho");
        UserEntity userEntity = optionalUser.get();
        RecipeAddDTO recipeDto = new RecipeAddDTO();
        recipeDto.setName("Cheesecake");
        recipeDto.setIngredients("Biscuits, butter, sugar, chocolate");
        recipeDto.setInstructions("Mix all the products and put in the oven");
        recipeDto.setImageUrl("photo");
        recipeDto.setTimeForCooking(60);


    }
}
