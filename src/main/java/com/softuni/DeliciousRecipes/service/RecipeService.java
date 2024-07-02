package com.softuni.DeliciousRecipes.service;

import com.softuni.DeliciousRecipes.model.dto.RecipeAddDTO;
import com.softuni.DeliciousRecipes.model.entity.Category;
import com.softuni.DeliciousRecipes.model.entity.Recipe;
import com.softuni.DeliciousRecipes.model.entity.UserEntity;
import com.softuni.DeliciousRecipes.model.enums.CategoryName;
import com.softuni.DeliciousRecipes.repository.CategoryRepository;
import com.softuni.DeliciousRecipes.repository.RecipeRepository;
import com.softuni.DeliciousRecipes.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public RecipeService(RecipeRepository recipeRepository, ModelMapper modelMapper, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
    public boolean add(RecipeAddDTO recipeAddDTO, String username) {
        UserEntity user = findByUsername(username);

        Recipe recipe = modelMapper.map(recipeAddDTO, Recipe.class);
        recipe.setAddedBy(user);

        Optional<Category> category = categoryRepository.findByName(recipeAddDTO.getCategory());

        recipe.setCategory(category.get());
        recipeRepository.save(recipe);
        return true;
    }

}
