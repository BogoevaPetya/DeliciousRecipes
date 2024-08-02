package com.softuni.DeliciousRecipes;

import com.softuni.DeliciousRecipes.model.entity.Recipe;
import com.softuni.DeliciousRecipes.repository.RecipeRepository;
import com.softuni.DeliciousRecipes.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest

public class RecipeControllerIT {
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    public  void testViewRecipeById(){
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Brownie");
        recipe.setTimeForCooking(10);
        recipeRepository.save(recipe);
    }
}
