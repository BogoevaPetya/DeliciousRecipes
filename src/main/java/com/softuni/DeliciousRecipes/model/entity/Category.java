package com.softuni.DeliciousRecipes.model.entity;

import com.softuni.DeliciousRecipes.model.enums.CategoryName;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity{
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private CategoryName name;
    @OneToMany(mappedBy = "category")
    private Set<Recipe> recipes;

    public Category() {
    }

    public CategoryName getName() {
        return name;
    }

    public void setName(CategoryName name) {
        this.name = name;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }
}
