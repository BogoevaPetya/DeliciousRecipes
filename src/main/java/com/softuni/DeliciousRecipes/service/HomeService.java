package com.softuni.DeliciousRecipes.service;

import com.softuni.DeliciousRecipes.model.entity.Recipe;
import com.softuni.DeliciousRecipes.model.entity.UserEntity;
import com.softuni.DeliciousRecipes.repository.RecipeRepository;
import com.softuni.DeliciousRecipes.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HomeService {
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final UserService userService;

    public HomeService(UserRepository userRepository, RecipeRepository recipeRepository, UserService userService) {
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
        this.userService = userService;
    }

    public void deleteRecipe(Long id) {

        for (UserEntity user : userRepository.findAll()) {
            int recipeIndex = -1;
            for (Recipe likedRecipe : user.getLikedRecipes()) {
                if (likedRecipe.getId().equals(id)) {
                    recipeIndex = user.getLikedRecipes().indexOf(likedRecipe);
                }
            }

            if (recipeIndex >= 0) {
                user.getLikedRecipes().remove(recipeIndex);
                userRepository.save(user);
            }
        }

        for (UserEntity user : userRepository.findAll()) {
            int recipeIndex = -1;
            for (Recipe favoriteRecipe : user.getFavoriteRecipes()) {
                if (favoriteRecipe.getId().equals(id)) {
                    recipeIndex = user.getFavoriteRecipes().indexOf(favoriteRecipe);
                }
            }

            if (recipeIndex >= 0) {
                user.getFavoriteRecipes().remove(recipeIndex);
                userRepository.save(user);
            }
        }

        this.recipeRepository.deleteById(id);
    }

    public void removeFromFavorites(Long id) {
        UserEntity user = userService.getLoggedUser();

        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isEmpty()) {
            return;
        }

        int index = -1;

        for (Recipe recipe : user.getFavoriteRecipes()) {
            Long id1 = recipe.getId();
            Long id2 = optionalRecipe.get().getId();
            if (id1.equals(id2)) {
                index = user.getFavoriteRecipes().indexOf(recipe);
            }
        }
        user.getFavoriteRecipes().remove(index);
        userRepository.save(user);
    }
}
