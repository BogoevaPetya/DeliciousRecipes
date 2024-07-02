package com.softuni.DeliciousRecipes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "login1";
    }


//    @PostMapping("/login-error")
//    public String loginError(RedirectAttributes redirectAttributes){
//        boolean wrongCredentials = true;
//        redirectAttributes.addFlashAttribute("wrongCredentials", wrongCredentials);
//        return "redirect:/users/login";
//    }

}
