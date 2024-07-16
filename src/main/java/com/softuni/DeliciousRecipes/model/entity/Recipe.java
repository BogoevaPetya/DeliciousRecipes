package com.softuni.DeliciousRecipes.model.entity;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe extends BaseEntity{
    @Column(nullable = false)
    private String name;
    @ManyToOne(optional = false)
    private Category category;
    @Column(nullable = false)
    private String ingredients;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String instructions;
    private Integer timeForCooking;
    @ManyToOne(optional = false)
    private UserEntity addedBy;
    private int likes;
    @Column(columnDefinition = "LONGTEXT")
    private String image;
    @OneToMany
    private List<Comment> comments;

    public Recipe() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Integer getTimeForCooking() {
        return timeForCooking;
    }

    public void setTimeForCooking(Integer timeForCooking) {
        this.timeForCooking = timeForCooking;
    }

    public UserEntity getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(UserEntity addedBy) {
        this.addedBy = addedBy;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
