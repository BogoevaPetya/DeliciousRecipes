package com.softuni.DeliciousRecipes.controller;

import com.softuni.DeliciousRecipes.model.dto.UserRegisterDTO;
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

    @ModelAttribute
    public UserRegisterDTO userRegisterDTO(){
        return new UserRegisterDTO();
    }
    
    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @GetMapping("/register")
    public String registerView(){
        return "register";
    }



}
