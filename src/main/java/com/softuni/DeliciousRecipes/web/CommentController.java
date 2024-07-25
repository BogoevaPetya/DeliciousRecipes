package com.softuni.DeliciousRecipes.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommentController {

    @GetMapping("/add-comment")
    public String addComment(){
        return "add-comment";
    }
}
