package com.softuni.DeliciousRecipes.model.dto;

import com.softuni.DeliciousRecipes.model.entity.Category;
import com.softuni.DeliciousRecipes.model.enums.CategoryName;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class RecipeAddDTO {
    @Size(min = 3, max = 30)
    @NotNull
    private String name;
    @NotNull
    private CategoryName category;
    @Size(min = 10)
    @NotNull
    private String ingredients;
    @Size(min = 20)
    @NotNull
    private String instructions;
    @PositiveOrZero
    private Integer timeForCooking;
    private String imageUrl;

    public RecipeAddDTO() {
    }

    public RecipeAddDTO(String name, CategoryName category, String ingredients, String instructions, Integer timeForCooking, String imageUrl) {
        this.name = name;
        this.category = category;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.timeForCooking = timeForCooking;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryName getCategory() {
        return category;
    }

    public void setCategory(CategoryName category) {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
