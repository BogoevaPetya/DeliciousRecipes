package com.softuni.DeliciousRecipes.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_liked_recipe")
public class UserLikedRecipe extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe;

    public UserLikedRecipe() {}

    public UserEntity getUser() {
        return user;
    }

    public UserLikedRecipe setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public UserLikedRecipe setRecipe(Recipe recipe) {
        this.recipe = recipe;
        return this;
    }
}
