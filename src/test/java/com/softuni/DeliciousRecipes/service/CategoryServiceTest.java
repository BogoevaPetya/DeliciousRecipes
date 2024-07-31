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

        when(mockCategoryRepository.findByName(categoryName)).thenReturn(Optional.of(category));

        Optional<Category> foundCategory = categoryServiceTestToTest.findByName(categoryName);

        assertTrue(foundCategory.isPresent());
        assertEquals(categoryName, foundCategory.get().getName());
    }

    @Test
    public void testFindByName_NotFound() {

        Category category = new Category();

        when(mockCategoryRepository.findByName(category.getName())).thenReturn(Optional.empty());

        Optional<Category> foundCategory = categoryServiceTestToTest.findByName(category.getName());

        assertFalse(foundCategory.isPresent());
    }
}