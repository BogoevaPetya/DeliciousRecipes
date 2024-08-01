package com.softuni.DeliciousRecipes.service;

import com.softuni.DeliciousRecipes.model.entity.Category;
import com.softuni.DeliciousRecipes.model.enums.CategoryName;
import com.softuni.DeliciousRecipes.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    private CategoryService categoryServiceTestToTest;

    @Mock
    private CategoryRepository mockCategoryRepository;



    @BeforeEach
    public void setUp() {
        this.categoryServiceTestToTest = new CategoryService(mockCategoryRepository);
    }

    @Test
    public void testFindByName() {
        CategoryName categoryName = CategoryName.MAIN_DISH;

        Category category = new Category();
        category.setName(categoryName);

        when(mockCategoryRepository.getByName(categoryName)).thenReturn(category);

        categoryServiceTestToTest.findByName(categoryName);

        assertEquals(category, categoryServiceTestToTest.findByName(categoryName));
    }

}