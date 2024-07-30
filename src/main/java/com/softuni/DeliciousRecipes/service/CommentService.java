package com.softuni.DeliciousRecipes.service;

import com.softuni.DeliciousRecipes.model.dto.AddCommentDTO;
import com.softuni.DeliciousRecipes.model.dto.CommentDTO;
import com.softuni.DeliciousRecipes.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

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
                .uri("/comments/add")
                .body(addCommentDTO)
                .retrieve();
    }

    public boolean hasComments(){
        if (this.commentRepository.count() > 0){
            return true;
        }
        return false;
    }

    public List<CommentDTO> getAllComments(){
        return restClient
                .get()
                .uri("/comments")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

    public void deleteComment(Long id) {
        this.restClient
                .delete()
                .uri("/comments/delete/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}
