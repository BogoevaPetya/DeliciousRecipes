package com.softuni.DeliciousRecipes.controller;

import com.softuni.DeliciousRecipes.model.dto.RecipeAddDTO;
import com.softuni.DeliciousRecipes.model.dto.RecipeFullInfoDTO;
import com.softuni.DeliciousRecipes.model.dto.RecipeShortInfoDTO;
import com.softuni.DeliciousRecipes.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @ModelAttribute
    public RecipeAddDTO recipeAddDTO(){
        return new RecipeAddDTO();
    }

    @GetMapping("/add")
    public String addRecipeView(){
        return "add-recipe";
    }

    @PostMapping("/add")
    public String addRecipe(@Valid RecipeAddDTO recipeAddDTO,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes,
                            @AuthenticationPrincipal UserDetails userDetails){

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("recipeAddDTO", recipeAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipeAddDTO", bindingResult);
            return "redirect:/recipes/add";
        }

        boolean success = recipeService.add(recipeAddDTO, userDetails.getUsername());

        if (!success){
            redirectAttributes.addAttribute("recipeAddDTO", recipeAddDTO);
            return "redirect:/recipes/add";
        }

        return "redirect:/home";
    }

    @GetMapping("/all")
    public String viewAll(){
        return "all-recipes";
    }

    @GetMapping("/salads")
    public String viewSalads(Model model){
        List<RecipeShortInfoDTO> allSalads = recipeService.getAllSalads();
        model.addAttribute("allSalads", allSalads);
        return "salads";
    }

    @GetMapping("/salads/{id}")
    public String viewSaladById(@PathVariable("id") Long id){
        RecipeFullInfoDTO recipe = recipeService.getRecipeById(id);
        return "recipe-info";
    }
}
