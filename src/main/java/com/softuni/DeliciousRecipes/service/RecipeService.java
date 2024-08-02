package com.softuni.DeliciousRecipes.service;

import com.softuni.DeliciousRecipes.model.dto.RecipeAddDTO;
import com.softuni.DeliciousRecipes.model.dto.RecipeFullInfoDTO;
import com.softuni.DeliciousRecipes.model.dto.RecipeShortInfoDTO;
import com.softuni.DeliciousRecipes.model.entity.Category;
import com.softuni.DeliciousRecipes.model.entity.Recipe;
import com.softuni.DeliciousRecipes.model.entity.UserEntity;
import com.softuni.DeliciousRecipes.model.enums.CategoryName;
import com.softuni.DeliciousRecipes.repository.RecipeRepository;
import com.softuni.DeliciousRecipes.repository.UserRepository;
import com.softuni.DeliciousRecipes.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public RecipeService(RecipeRepository recipeRepository, ModelMapper modelMapper, UserRepository userRepository, UserService userService, CategoryService categoryService) {
        this.recipeRepository = recipeRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }


    public boolean add(RecipeAddDTO recipeAddDTO) {
        UserEntity user = userService.getLoggedUser();

        Recipe recipe = modelMapper.map(recipeAddDTO, Recipe.class);
        recipe.setAddedBy(user);

        Category category = categoryService.findByName(recipeAddDTO.getCategory());
        recipe.setCategory(category);

        recipeRepository.save(recipe);
        return true;
    }

    public List<RecipeShortInfoDTO> getAllRecipesByCategory(CategoryName categoryName) {
        List<Recipe> recipes = this.recipeRepository.findByCategoryName(categoryName);

        return recipes.stream()
                .map(r -> modelMapper.map(r, RecipeShortInfoDTO.class))
                .collect(Collectors.toList());
    }

    public RecipeFullInfoDTO getRecipeById(Long id) {

        Optional<Recipe> optional = recipeRepository.findById(id);

        if (optional.isEmpty()) {
            throw new ObjectNotFoundException(id);
        }

        return modelMapper.map(optional.get(), RecipeFullInfoDTO.class);
    }

    public void addToFavorites(Long id) {
        UserEntity user = userService.getLoggedUser();

        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isEmpty()) {
            return;
        }

        Recipe recipe = optionalRecipe.get();

        for (Recipe favoriteRecipe : user.getFavoriteRecipes()) {
            if (favoriteRecipe.getId().equals(recipe.getId())) {
                return;
            }
        }

        user.getFavoriteRecipes().add(recipe);
        userRepository.save(user);
    }

    public void likeRecipe(Long id) {
        UserEntity user = userService.getLoggedUser();

        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isEmpty()) {
            return;
        }

        Recipe recipe = optionalRecipe.get();

        for (Recipe likedRecipe : user.getLikedRecipes()) {
            if (likedRecipe.getId().equals(recipe.getId())) {
                return;
            }
        }

        recipe.setLikes(recipe.getLikes() + 1);
        recipeRepository.save(recipe);
        user.getLikedRecipes().add(recipe);

        userRepository.save(user);
    }

    @PreAuthorize("@recipeService.isActualUser(#id) || hasRole('ADMINISTRATOR')")
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

    public boolean isActualUser(Long id){
        return recipeRepository.findById(id)
                .filter(e -> e.getAddedBy().getUsername().equals(userService.getLoggedUserUsername())).isPresent();
    }

}
