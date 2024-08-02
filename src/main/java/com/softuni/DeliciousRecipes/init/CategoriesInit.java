package com.softuni.DeliciousRecipes.init;

import com.softuni.DeliciousRecipes.model.entity.Category;
import com.softuni.DeliciousRecipes.model.enums.CategoryName;
import com.softuni.DeliciousRecipes.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoriesInit implements CommandLineRunner {
    private final CategoryRepository categoryRepository;

    public CategoriesInit(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() <= 0){
            List<Category> categories = new ArrayList<>();

            Category soup = new Category();
            soup.setName(CategoryName.SOUP);
            categories.add(soup);

            Category salad = new Category();
            soup.setName(CategoryName.SALAD);
            categories.add(soup);

            Category dessert = new Category();
            dessert.setName(CategoryName.DESSERT);
            categories.add(dessert);

            Category mainDish = new Category();
            mainDish.setName(CategoryName.MAIN_DISH);
            categories.add(mainDish);

            categoryRepository.saveAll(categories);
        }

    }
}
