package com.softuni.DeliciousRecipes.service;

import com.softuni.DeliciousRecipes.model.dto.RecipeAddDTO;
import com.softuni.DeliciousRecipes.model.dto.RecipeFullInfoDTO;
import com.softuni.DeliciousRecipes.model.dto.RecipeShortInfoDTO;
import com.softuni.DeliciousRecipes.model.entity.Category;
import com.softuni.DeliciousRecipes.model.entity.Recipe;
import com.softuni.DeliciousRecipes.model.entity.UserEntity;
import com.softuni.DeliciousRecipes.model.enums.CategoryName;
import com.softuni.DeliciousRecipes.repository.CategoryRepository;
import com.softuni.DeliciousRecipes.repository.RecipeRepository;
import com.softuni.DeliciousRecipes.repository.UserRepository;
import com.softuni.DeliciousRecipes.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public RecipeService(RecipeRepository recipeRepository, ModelMapper modelMapper, UserRepository userRepository, CategoryRepository categoryRepository, UserService userService, CategoryService categoryService) {
        this.recipeRepository = recipeRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }


    public boolean add(RecipeAddDTO recipeAddDTO, String username) {
        Optional<UserEntity> optionalUser = userService.findUserByUsername(username);
        UserEntity user = optionalUser.get();

        Recipe recipe = modelMapper.map(recipeAddDTO, Recipe.class);
        recipe.setAddedBy(user);

        Optional<Category> category = categoryService.findByName(recipeAddDTO.getCategory());
        recipe.setCategory(category.get());
        recipeRepository.save(recipe);
        return true;
    }


    public List<RecipeShortInfoDTO> getAllSalads() {
        CategoryName categoryName = CategoryName.SALAD;
        return getAllRecipesByCategory(categoryName);
    }

    public List<RecipeShortInfoDTO> getAllSoups() {
        CategoryName categoryName = CategoryName.SOUP;
        return getAllRecipesByCategory(categoryName);
    }


    public List<RecipeShortInfoDTO> getAllMainDishes() {
        CategoryName categoryName = CategoryName.MAIN_DISH;
        return getAllRecipesByCategory(categoryName);
    }

    public List<RecipeShortInfoDTO> getAllDesserts() {
        CategoryName categoryName = CategoryName.DESSERT;
        return getAllRecipesByCategory(categoryName);
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

        user.getFavoriteRecipes().add(optionalRecipe.get());
        userRepository.save(user);
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


}
