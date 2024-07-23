package com.softuni.DeliciousRecipes.web;

import com.softuni.DeliciousRecipes.model.dto.RecipeFavoriteDTO;
import com.softuni.DeliciousRecipes.model.dto.UserDetailsDTO;
import com.softuni.DeliciousRecipes.model.dto.UserInfoDTO;
import com.softuni.DeliciousRecipes.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model){

        if (userDetails instanceof UserDetailsDTO userDetailsDTO){
            UserInfoDTO userInfo = userService.getUserDetails(userDetailsDTO.getId());
            List<RecipeFavoriteDTO> favorites = userInfo.getFavorites();

            model.addAttribute("myInfo", userInfo);
            model.addAttribute("favorites", userInfo.getFavorites());
        }

        return "home";
    }
}

