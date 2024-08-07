package com.softuni.DeliciousRecipes.model.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddCommentDTO {
    @Size(min = 3, max = 30)
    @NotNull
    private String authorName;
    @Size(min = 5)
    @NotNull
    private String comment;
    @Min(1)
    @Max(5)
    @NotNull
    private Integer rate;

    public AddCommentDTO() {}

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }
}
