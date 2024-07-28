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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public RecipeService(RecipeRepository recipeRepository, ModelMapper modelMapper, UserRepository userRepository, CategoryRepository categoryRepository, UserService userService) {
        this.recipeRepository = recipeRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
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

    public List<RecipeShortInfoDTO> getAllSalads(){
        List<Recipe> recipes = this.recipeRepository.findByCategoryName(CategoryName.SALAD);


        return recipes.stream()
                .map(r -> modelMapper.map(r, RecipeShortInfoDTO.class))
                .collect(Collectors.toList());
    }

    public List<RecipeShortInfoDTO> getAllSoups() {
        List<Recipe> recipes = this.recipeRepository.findByCategoryName(CategoryName.SOUP);

        return recipes.stream()
                .map(r -> modelMapper.map(r, RecipeShortInfoDTO.class))
                .collect(Collectors.toList());
    }

    public List<RecipeShortInfoDTO> getAllMainDishes() {
        List<Recipe> recipes = this.recipeRepository.findByCategoryName(CategoryName.MAIN_DISH);

        return recipes.stream()
                .map(r -> modelMapper.map(r, RecipeShortInfoDTO.class))
                .collect(Collectors.toList());
    }

    public List<RecipeShortInfoDTO> getAllDesserts() {
        List<Recipe> recipes = this.recipeRepository.findByCategoryName(CategoryName.DESSERT);

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
        if (optionalRecipe.isEmpty()){
            return;
        }

        user.getFavoriteRecipes().add(optionalRecipe.get());
        userRepository.save(user);
    }

//    public void likeRecipe(Long id){
//        String username = this.userService.getLoggedUsername();
//
//        Optional<UserEntity> optionalUser = this.userService.findUserByUsername(username);
//        if (optionalUser.isEmpty()) {
//            return;
//        }
//
//        UserEntity user = optionalUser.get();
//
//        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
//        if (optionalRecipe.isEmpty()){
//            return;
//        }
//
//        Recipe recipe = optionalRecipe.get();
//        boolean isExisting = false;
//
//        for (Recipe likedRecipe : user.getLikedRecipes()) {
//            if (likedRecipe.getId().equals(recipe.getId())){
//                isExisting = true;
//            }
//        }
//
//        if (!isExisting){
//            recipe.setLikes(recipe.getLikes() + 1);
//            user.getLikedRecipes().add(recipe);
//            this.recipeRepository.save(recipe);
//        }
//        userRepository.save(user);
//    }

    public void deleteRecipe(Long id) {
        this.recipeRepository.deleteById(id);
    }

    public void removeFromFavorites(Long id) {
        String username = this.userService.getLoggedUsername();

        Optional<UserEntity> optionalUser = this.userService.findUserByUsername(username);
        if (optionalUser.isEmpty()) {
            return;
        }

        UserEntity user = optionalUser.get();

        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isEmpty()){
            return;
        }

        int index = -1;

        for (Recipe recipe : user.getFavoriteRecipes()) {
            Long id1 = recipe.getId();
            Long id2 = optionalRecipe.get().getId();
            if (id1.equals(id2)){
                index = user.getFavoriteRecipes().indexOf(recipe);
            }
        }
        user.getFavoriteRecipes().remove(index);
        userRepository.save(user);
    }
}
