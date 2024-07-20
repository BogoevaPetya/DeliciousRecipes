package com.softuni.DeliciousRecipes.model.dto;

import java.util.List;

public class UserInfoDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private List<String> roles;
    private List<CommentDTO> comments;
    private List<RecipeFavoriteDTO> favorites;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public List<RecipeFavoriteDTO> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<RecipeFavoriteDTO> favorites) {
        this.favorites = favorites;
    }
}

