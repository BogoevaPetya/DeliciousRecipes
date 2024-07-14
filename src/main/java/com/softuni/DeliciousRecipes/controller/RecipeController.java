package com.softuni.DeliciousRecipes.controller;

import com.softuni.DeliciousRecipes.model.dto.RecipeAddDTO;
import com.softuni.DeliciousRecipes.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

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
                            @RequestParam("image")MultipartFile file,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes,
                            @AuthenticationPrincipal UserDetails userDetails){

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("recipeAddDTO", recipeAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipeAddDTO", bindingResult);
            return "redirect:/recipes/add";
        }

        boolean success = recipeService.add(recipeAddDTO, userDetails.getUsername(), file);

        if (!success){
            redirectAttributes.addAttribute("recipeAddDTO", recipeAddDTO);
            return "redirect:/recipes/add";
        }

        return "redirect:/home";
    }

    @GetMapping("/salads")
    public String saladsView(){
        return "salads";
    }
}
