package com.softuni.DeliciousRecipes.model.dto;

import com.softuni.DeliciousRecipes.model.entity.UserEntity;

public class CommentDTO {
    private String comment;
    private UserEntity author;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }
}
