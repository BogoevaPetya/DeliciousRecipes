package com.softuni.DeliciousRecipes.service;

import com.softuni.DeliciousRecipes.model.dto.AddCommentDTO;
import com.softuni.DeliciousRecipes.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final RestClient restClient;

    public CommentService(CommentRepository commentRepository, RestClient restClient) {
        this.commentRepository = commentRepository;
        this.restClient = restClient;
    }

    public void addComment(AddCommentDTO addCommentDTO) {
        restClient
                .post()
                .uri("/comments")
                .body(addCommentDTO)
                .retrieve();
    }

}
