package com.softuni.DeliciousRecipes.web;

import com.softuni.DeliciousRecipes.repository.CommentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommentController {
    private final CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping("/add-comment")
    public String addComment(){
        return "add-comment";
    }

    @GetMapping("/comments")
    public String allComments(Model model){
        if (commentRepository.count() < 0){
            model.addAttribute("isEmpty", true);
        }
        return "comments";
    }
}
