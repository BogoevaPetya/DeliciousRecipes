package com.softuni.DeliciousRecipes.web;

import com.softuni.DeliciousRecipes.model.dto.RecipeAddDTO;
import com.softuni.DeliciousRecipes.model.dto.RecipeFullInfoDTO;
import com.softuni.DeliciousRecipes.model.dto.RecipeShortInfoDTO;
import com.softuni.DeliciousRecipes.model.enums.CategoryName;
import com.softuni.DeliciousRecipes.service.RecipeService;
import com.softuni.DeliciousRecipes.service.exception.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
                            RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("recipeAddDTO", recipeAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipeAddDTO", bindingResult);
            return "redirect:/recipes/add";
        }

        boolean success = recipeService.add(recipeAddDTO);

        if (!success){
            redirectAttributes.addAttribute("recipeAddDTO", recipeAddDTO);
            return "redirect:/recipes/add";
        }

        return "redirect:/recipes/all";
    }

    @GetMapping("/all")
    public String viewAll(Model model){
        model.addAttribute("saladsCount", recipeService.getAllRecipesByCategory(CategoryName.SALAD));
        model.addAttribute("soupsCount", recipeService.getAllRecipesByCategory(CategoryName.SOUP));
        model.addAttribute("mainDishesCount", recipeService.getAllRecipesByCategory(CategoryName.MAIN_DISH));
        model.addAttribute("dessertsCount", recipeService.getAllRecipesByCategory(CategoryName.DESSERT));
        return "all-recipes";
    }


    @GetMapping("/salads")
    public String viewSalads(Model model){
        List<RecipeShortInfoDTO> allSalads = recipeService.getAllRecipesByCategory(CategoryName.SALAD);
        model.addAttribute("allSalads", allSalads);
        return "salads";
    }

    @GetMapping("/soups")
    public String viewSoups(Model model){
        List<RecipeShortInfoDTO> allSoups = recipeService.getAllRecipesByCategory(CategoryName.SOUP);
        model.addAttribute("allSoups", allSoups);
        return "soups";
    }

    @GetMapping("/main-dishes")
    public String viewMainDishes(Model model){
        List<RecipeShortInfoDTO> allMainDishes = recipeService.getAllRecipesByCategory(CategoryName.MAIN_DISH);
        model.addAttribute("allMainDishes", allMainDishes);
        return "main-dishes";
    }

    @GetMapping("/desserts")
    public String viewDesserts(Model model){
        List<RecipeShortInfoDTO> allDesserts = recipeService.getAllRecipesByCategory(CategoryName.DESSERT);
        model.addAttribute("allDesserts", allDesserts);
        return "desserts";
    }

    @GetMapping("/{id}")
    public String viewRecipeById(@PathVariable("id") Long id, Model model){
        RecipeFullInfoDTO recipe = recipeService.getRecipeById(id);
        model.addAttribute("recipeInfo", recipe);
        return "recipe-info";
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleOnfe(Model model, ObjectNotFoundException onfe){
        model.addAttribute("message", onfe.getMessage());
        return "object-not-found";
    }

    @GetMapping("/add-to-favorite/{id}")
    public String addToFavorite(@PathVariable Long id){
        recipeService.addToFavorites(id);
        return "redirect:/recipes/{id}";
    }

    @GetMapping("/like-recipe/{id}")
    public String recipeLike(@PathVariable Long id){
        recipeService.likeRecipe(id);
        return "redirect:/recipes/{id}";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable Long id){
        recipeService.deleteRecipe(id);
        return "redirect:/home";
    }


    @DeleteMapping("/remove/{id}")
    public String removeFavoriteRecipe(@PathVariable Long id){
        recipeService.removeFromFavorites(id);
        return "redirect:/home";
    }


}
