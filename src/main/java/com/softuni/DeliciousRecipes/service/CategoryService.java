package com.softuni.DeliciousRecipes.service;

import com.softuni.DeliciousRecipes.model.entity.Category;
import com.softuni.DeliciousRecipes.model.enums.CategoryName;
import com.softuni.DeliciousRecipes.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Optional<Category> findByName(CategoryName name) {
        return this.categoryRepository.findByName(name);
    }
}
