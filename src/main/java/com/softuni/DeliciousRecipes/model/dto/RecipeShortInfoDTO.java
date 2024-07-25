package com.softuni.DeliciousRecipes.model.dto;

import com.softuni.DeliciousRecipes.model.enums.CategoryName;

public class RecipeShortInfoDTO {
    private Long id;
    private String name;
    private String image;
    private int timeForCooking;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTimeForCooking() {
        return timeForCooking;
    }

    public void setTimeForCooking(int timeForCooking) {
        this.timeForCooking = timeForCooking;
    }
}
