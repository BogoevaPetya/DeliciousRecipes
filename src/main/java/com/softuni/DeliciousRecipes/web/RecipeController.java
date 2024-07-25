package com.softuni.DeliciousRecipes.web;

import com.softuni.DeliciousRecipes.model.dto.RecipeAddDTO;
import com.softuni.DeliciousRecipes.model.dto.RecipeFullInfoDTO;
import com.softuni.DeliciousRecipes.model.dto.RecipeShortInfoDTO;
import com.softuni.DeliciousRecipes.model.enums.CategoryName;
import com.softuni.DeliciousRecipes.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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

    @ModelAttribute
    public RecipeFullInfoDTO recipeFullInfoDTO(){
        return new RecipeFullInfoDTO();
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

        return "redirect:/recipes/all";
    }

    @GetMapping("/all")
    public String viewAll(Model model){
        model.addAttribute("saladsCount", recipeService.getAllSalads());
        model.addAttribute("soupsCount", recipeService.getAllSoups());
        model.addAttribute("mainDishesCount", recipeService.getAllMainDishes());
        model.addAttribute("dessertsCount", recipeService.getAllDesserts());
        return "all-recipes";
    }


    @GetMapping("/salads")
    public String viewSalads(Model model){
        List<RecipeShortInfoDTO> allSalads = recipeService.getAllSalads();
        model.addAttribute("allSalads", allSalads);
        return "salads";
    }

    @GetMapping("/soups")
    public String viewSoups(Model model){
        List<RecipeShortInfoDTO> allSoups = recipeService.getAllSoups();
        model.addAttribute("allSoups", allSoups);
        return "soups";
    }

    @GetMapping("/main-dishes")
    public String viewMainDishes(Model model){
        List<RecipeShortInfoDTO> allMainDishes = recipeService.getAllMainDishes();
        model.addAttribute("allMainDishes", allMainDishes);
        return "main-dishes";
    }

    @GetMapping("/desserts")
    public String viewDesserts(Model model){
        List<RecipeShortInfoDTO> allDesserts = recipeService.getAllDesserts();
        model.addAttribute("allDesserts", allDesserts);
        return "desserts";
    }

    @GetMapping("/{id}")
    public String viewRecipeById(@PathVariable("id") Long id, Model model){
        RecipeFullInfoDTO recipe = recipeService.getRecipeById(id);
        model.addAttribute("recipeInfo", recipe);
        return "recipe-info";
    }

    @GetMapping("/add-to-favorite/{id}")
    public String addToFavorite(@PathVariable Long id){
        recipeService.addToFavorites(id);
        return "redirect:/home";
    }


}
