package com.softuni.DeliciousRecipes.service;

import com.softuni.DeliciousRecipes.model.entity.Recipe;
import com.softuni.DeliciousRecipes.model.entity.UserEntity;
import com.softuni.DeliciousRecipes.model.entity.UserLikedRecipe;
import com.softuni.DeliciousRecipes.repository.RecipeRepository;
import com.softuni.DeliciousRecipes.repository.UserLikedRecipeRepository;
import com.softuni.DeliciousRecipes.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLikedRecipeService {
    private final UserLikedRecipeRepository userLikedRecipeRepository;
    private final UserService userService;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public UserLikedRecipeService(UserLikedRecipeRepository userLikedRecipeRepository, UserService userService, RecipeRepository recipeRepository, UserRepository userRepository) {
        this.userLikedRecipeRepository = userLikedRecipeRepository;
        this.userService = userService;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    public void likeRecipe(Long id) {
        String username = this.userService.getLoggedUsername();

        Optional<UserEntity> optionalUser = this.userService.findUserByUsername(username);
        if (optionalUser.isEmpty()) {
            return;
        }

        UserEntity user = optionalUser.get();

        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isEmpty()) {
            return;
        }

        Recipe recipe = optionalRecipe.get();

        UserLikedRecipe userLikedRecipe = new UserLikedRecipe();
        userLikedRecipe.setUser(user);
        userLikedRecipe.setRecipe(recipe);

        userLikedRecipeRepository.save(userLikedRecipe);
    }




}
