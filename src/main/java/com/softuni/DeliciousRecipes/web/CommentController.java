package com.softuni.DeliciousRecipes.web;

import com.softuni.DeliciousRecipes.model.dto.AddCommentDTO;
import com.softuni.DeliciousRecipes.model.dto.CommentDTO;
import com.softuni.DeliciousRecipes.model.dto.RecipeAddDTO;
import com.softuni.DeliciousRecipes.repository.CommentRepository;
import com.softuni.DeliciousRecipes.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ModelAttribute
    public AddCommentDTO addCommentDTO(){
        return new AddCommentDTO();
    }

    @GetMapping("/add")
    public String addComment(){
        return "add-comment";
    }

    @PostMapping("/add")
    public String addComment(@Valid AddCommentDTO addCommentDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addCommentDTO", addCommentDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addCommentDTO", bindingResult);
            return "redirect:/comments/add";
        }

        commentService.addComment(addCommentDTO);
        return "redirect:/home";
    }

    @GetMapping
    public String allComments(Model model){
        if (!commentService.hasComments()){
            model.addAttribute("isEmpty", true);
        }

        List<CommentDTO> allComments = commentService.getAllComments();
        model.addAttribute("allComments", allComments);
        return "comments";
    }
}
