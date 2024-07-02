package com.softuni.DeliciousRecipes.controller;

import com.softuni.DeliciousRecipes.model.dto.UserRegisterDTO;
import com.softuni.DeliciousRecipes.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public UserRegisterDTO userRegisterDTO(){
        return new UserRegisterDTO();
    }
    
    @GetMapping("/login")
    public String login(){
        return "login1";
    }

    @PostMapping("/login-error")
    public String loginError(RedirectAttributes redirectAttributes){
        boolean wrongCredentials = true;
        redirectAttributes.addFlashAttribute("wrongCredentials", wrongCredentials);
        return "redirect:/users/login";
    }


    @GetMapping("/register")
    public String registerView(){
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegisterDTO userRegisterDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() || !userService.registerUser(userRegisterDTO)){
            redirectAttributes.addFlashAttribute("userRegisterDTO", userRegisterDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDTO", bindingResult);
            return "redirect:/users/register";
        }

        return "redirect:/users/login";
    }



}
