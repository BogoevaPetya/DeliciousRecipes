package com.softuni.DeliciousRecipes.web;

import com.softuni.DeliciousRecipes.model.dto.FoodInfoDTO;
import com.softuni.DeliciousRecipes.model.dto.UserDetailsDTO;
import com.softuni.DeliciousRecipes.model.dto.UserInfoDTO;
import com.softuni.DeliciousRecipes.service.HomeService;
import com.softuni.DeliciousRecipes.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    private final UserService userService;
    private final HomeService homeService;

    public HomeController(UserService userService, HomeService homeService) {
        this.userService = userService;
        this.homeService = homeService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model){

        if (userDetails instanceof UserDetailsDTO userDetailsDTO){
            UserInfoDTO userInfo = userService.getUserDetails(userDetailsDTO.getId());
            List<FoodInfoDTO> favorites = userInfo.getFavorites();
            List<FoodInfoDTO> addedByMe = userInfo.getAddedByMe();

            model.addAttribute("myInfo", userInfo);
            model.addAttribute("favorites", favorites);
            model.addAttribute("addedByMe", addedByMe);
        }

        return "home";
    }


    @DeleteMapping("/recipes/delete/{id}")
    public String deleteRecipe(@PathVariable Long id){
        homeService.deleteRecipe(id);
        return "redirect:/home";
    }


    @DeleteMapping("/recipes/remove/{id}")
    public String removeFavoriteRecipe(@PathVariable Long id){
        homeService.removeFromFavorites(id);
        return "redirect:/home";
    }
}

