package com.softuni.DeliciousRecipes.web;

import com.softuni.DeliciousRecipes.model.dto.UserRegisterDTO;
import com.softuni.DeliciousRecipes.service.UserService;
import com.softuni.DeliciousRecipes.web.aop.WarnIfExecutionExceeds;
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
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public UserRegisterDTO userRegisterDTO(){
        return new UserRegisterDTO();
    }

    @GetMapping("/register")
    public String registerView(){
        return "register";
    }

    @WarnIfExecutionExceeds(timeInMillis = 2000)
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
