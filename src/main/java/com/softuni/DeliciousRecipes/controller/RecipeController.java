package com.softuni.DeliciousRecipes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecipeController {

    @GetMapping("/recipes/add")
    public String addRecipeView(){
        return "add-recipe";
    }
}