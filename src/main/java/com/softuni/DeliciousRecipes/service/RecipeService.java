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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RestClient restClient;

    public RecipeService(RecipeRepository recipeRepository, ModelMapper modelMapper, UserRepository userRepository, CategoryRepository categoryRepository, @Qualifier("recipesRestClient") RestClient restClient) {
        this.recipeRepository = recipeRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.restClient = restClient;
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
    public boolean add(RecipeAddDTO recipeAddDTO, String username) {
//        restClient
//                .post()
//                .uri("http://localhost8081/recipes")
//                .body(recipeAddDTO)
//                .retrieve();

        UserEntity user = findByUsername(username);

        Recipe recipe = modelMapper.map(recipeAddDTO, Recipe.class);
        recipe.setAddedBy(user);

        Optional<Category> category = categoryRepository.findByName(recipeAddDTO.getCategory());
        recipe.setCategory(category.get());
        recipeRepository.save(recipe);
        return true;
    }

    public List<RecipeShortInfoDTO> getAllSalads(){
        List<Recipe> recipes = this.recipeRepository.findByCategoryName(CategoryName.SALAD);


        return recipes.stream()
                .map(r -> modelMapper.map(r, RecipeShortInfoDTO.class))
                .collect(Collectors.toList());
    }


    public RecipeFullInfoDTO getRecipeById(Long id) {
        Optional<Recipe> optional = recipeRepository.findById(id);

        if (optional.isEmpty()){
            //TODO
        }

        return modelMapper.map(optional.get(), RecipeFullInfoDTO.class);
    }
}
